package com.orvils.domain.service

import com.orvils.domain.dto.BookDTO
import com.orvils.domain.exception.EntityAlreadyExistsException
import com.orvils.domain.exception.EntityNotFoundException
import com.orvils.domain.filter.BookFilter
import com.orvils.domain.model.Book
import com.orvils.common.mapper.CommonMapper
import com.orvils.common.message.Messages
import com.orvils.infrastructure.entity.BookEntity
import com.orvils.infrastructure.repository.resource.pagination.Page
import com.orvils.infrastructure.repository.resource.pagination.Pageable
import com.orvils.infrastructure.repository.BookRepository
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class BookService(
    private val authorService: AuthorService,
    private val publisherService: PublisherService,
    private val collectionService: CollectionService,
    private val bookRepository: BookRepository
) {

    fun create(bookDTO: BookDTO) : Book {
        getOne(bookDTO.isbn!!)?.let {
            throw EntityAlreadyExistsException(Messages.BOOK_ISBN_ALREADY_REGISTERED, bookDTO.isbn)
        }

        val author = authorService.getOne(bookDTO.author.id!!)
        val publisher = publisherService.getOne(bookDTO.publisher.id!!)
        val collection = bookDTO.collection.id?.let { collectionService.getOne(it) }

        val toPersist = Book(
            title = bookDTO.title!!,
            isbn = bookDTO.isbn,
            summary = bookDTO.summary,
            editionType = bookDTO.editionType!!,
            editionNumber = bookDTO.editionNumber!!,
            author = author,
            publisher = publisher,
            collection = collection
        )

        val entity = BookEntity {
            id = toPersist.id
            title = toPersist.title
            isbn = toPersist.isbn
            summary = toPersist.summary
            editionType = toPersist.editionType.name
            editionNumber = toPersist.editionNumber
        }

        return bookRepository.save(toPersist)
    }

    fun update(id: UUID, changes: MutableMap<String, Any?>) : Book {
        val targetBook: Book = getOne(id)

        changes["isbn"]?.takeIf { isbn -> isbn != targetBook.isbn }?.let { isbn ->
            getOne(isbn as String)?.takeIf { it.id != targetBook.id }?.let {
                throw EntityAlreadyExistsException(Messages.BOOK_ISBN_ALREADY_REGISTERED, it.isbn)
            }
        }

        changes["authorId"]?.let {
            changes["author"] = authorService.getOne(UUID.fromString(it as String))
        }

        changes["publisherId"]?.let {
            changes["publisher"] = publisherService.getOne(UUID.fromString(it as String))
        }

        changes["collectionId"]?.let {
            changes["collection"] = collectionService.getOne(UUID.fromString(it as String))
        }

        val toUpdate = CommonMapper.merge(changes, targetBook, Book::class)

        return bookRepository.save(toUpdate) //TODO: run the API and test this flow
    }

    fun getOne(isbn: String) : Book? {
        return bookRepository.findByIsbn(isbn)
    }

    fun getOne(id: UUID) : Book {
        return getOneOrNull(id) ?: throw EntityNotFoundException(Messages.BOOK_NOT_FOUND)
    }

    fun getOneOrNull(id: UUID) : Book? {
        return bookRepository.findById(id)
    }

    fun delete(id: UUID) {
        bookRepository.delete(id)
    }

    fun filter(filter: BookFilter, pageable: Pageable) : Page<Book> {
        return bookRepository.filter(filter, pageable)
    }

}
