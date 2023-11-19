package com.orvils.domain.service

import com.orvils.domain.dto.CollectionDTO
import com.orvils.domain.exception.EntityNotFoundException
import com.orvils.domain.model.Collection
import com.orvils.common.message.Messages
import com.orvils.infrastructure.repository.CollectionRepository
import org.koin.core.annotation.Single
import java.util.*

@Single
class CollectionService(
    private val publisherService: PublisherService,
    private val collectionRepository: CollectionRepository
) {

    fun create(collection: CollectionDTO) : Collection {
        val publisher = publisherService.getOne(collection.publisher.id!!)

        val toPersist = Collection(
            name = collection.name,
            description = collection.description,
            publisher = publisher
        )

        return collectionRepository.save(toPersist)
    }

    fun update(collection: Collection) : Collection {
        return collectionRepository.save(collection)
    }

    fun getOne(id: UUID) : Collection {
        return getOneOrNull(id) ?: throw EntityNotFoundException(Messages.COLLECTION_NOT_FOUND)
    }

    fun getOneOrNull(id: UUID) : Collection? {
        return collectionRepository.findById(id)
    }

}
