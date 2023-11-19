package com.orvils.config.serialization

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.util.DefaultIndenter
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter
import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule.Builder
import com.orvils.common.`object`.OptionalValued
import com.orvils.config.serialization.deserializer.OptionalValuedDeserializer

class JsonParser {

    companion object {
        val instance: ObjectMapper

        init {
            instance = configMapper()
        }

        private fun configMapper() : ObjectMapper{
            return ObjectMapper().apply {

                setConfig(this.serializationConfig.with(MapperFeature.USE_ANNOTATIONS))
                setConfig(this.deserializationConfig.with(MapperFeature.USE_ANNOTATIONS))
                setDefaultPropertyInclusion(JsonInclude.Include.NON_NULL)
                setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE)
                setDefaultPrettyPrinter(DefaultPrettyPrinter().apply {
                    indentArraysWith(DefaultIndenter.SYSTEM_LINEFEED_INSTANCE)
                })

                disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                registerModules(
                    JavaTimeModule(),
                    Builder().enable(KotlinFeature.NullIsSameAsDefault).build(),
                    SimpleModule().apply { addDeserializer(OptionalValued::class.java, OptionalValuedDeserializer()) }
                )
            }
        }

    }

}