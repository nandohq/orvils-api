package com.orvils.domain.service

import com.orvils.domain.exception.EntityNotFoundException
import com.orvils.domain.model.Country
import com.orvils.common.message.Messages
import com.orvils.infrastructure.repository.CountryRepository
import org.koin.core.annotation.Single
import java.util.UUID

@Single
class CountryService(private val countryRepository: CountryRepository) {

    fun getOne(id: UUID) : Country {
        return getOneOrNull(id) ?: throw EntityNotFoundException(Messages.COUNTRY_NOT_FOUND)
    }

    fun getOneOrNull(id: UUID) : Country? {
        return countryRepository.findById(id)
    }
}