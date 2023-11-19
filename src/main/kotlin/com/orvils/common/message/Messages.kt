package com.orvils.common.message

import java.text.MessageFormat

class Messages {

    companion object {
        private val propertiesHandler = PropertiesHandler() // TODO: add path to properties file in application

        const val API_INPUT_NOT_UUID = "api.input_value.not_uuid"
        const val API_INPUT_NOT_NUMBER = "api.input_value.not_number"
        const val API_INPUT_INVALID_ISBN = "api.input_value.invalid_isbn"
        const val API_INPUT_NOT_INFORMED = "api.input_value.not_informed"
        const val API_INPUT_NOT_ENUMERATION = "api.input_value.not_enumeration"

        const val AUTHOR_NOT_FOUND = "domain.author.not_found"

        const val BOOK_NOT_FOUND = "domain.book.not_found"
        const val BOOK_ISBN_ALREADY_REGISTERED = "domain.book.isbn_already_registered"

        const val COLLECTION_NOT_FOUND = "domain.collection.not_found"

        const val COUNTRY_NOT_FOUND = "domain.country.not_found"

        const val PUBLISHER_NOT_FOUND = "domain.publisher.not_found"

        fun get(key: String, vararg params: Any): String {
            val message = propertiesHandler.propertyValue(key)
            if (params.isEmpty()) return message

            return MessageFormat.format(message, *params)
        }
    }

}