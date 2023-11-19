package com.orvils.domain.service

import com.orvils.domain.dto.PublisherDTO
import com.orvils.domain.exception.EntityNotFoundException
import com.orvils.domain.model.Publisher
import com.orvils.common.message.Messages
import com.orvils.infrastructure.repository.PublisherRepository
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class PublisherService(
    private val countryService: CountryService,
    private val publisherRepository: PublisherRepository
) {

    fun create(publisher: PublisherDTO) : Publisher {
        val country = publisher.country.id?.let { countryService.getOne(it) }
        val toPersist = Publisher(
            name = publisher.name,
            description = publisher.description,
            country = country
        )

        return publisherRepository.save(toPersist)
    }

    fun update(publisher: Publisher) : Publisher {
        return publisherRepository.save(publisher)
    }

    fun getOne(id: UUID) : Publisher {
        return getOneOrNull(id) ?: throw EntityNotFoundException(Messages.PUBLISHER_NOT_FOUND)
    }

    fun getOneOrNull(id: UUID) : Publisher? {
        return publisherRepository.findById(id)
    }

}