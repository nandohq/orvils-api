package com.orvils.domain.service

import com.orvils.domain.exception.EntityNotFoundException
import com.orvils.domain.model.Author
import com.orvils.common.message.Messages
import com.orvils.infrastructure.repository.AuthorRepository
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class AuthorService(
    private val countryService: CountryService,
    private val authorRepository: AuthorRepository
) {

    fun create(author: Author) : Author {
        author.nationality?.id?.let { countryService.getOne(it) }
        return authorRepository.save(author)
    }

    fun update(author: Author) : Author {
        return authorRepository.save(author)
    }

    fun getOne(id: UUID) : Author {
        return getOneOrNull(id) ?: throw EntityNotFoundException(Messages.AUTHOR_NOT_FOUND)
    }

    fun getOneOrNull(id: UUID) : Author? {
        return authorRepository.findById(id)
    }

}