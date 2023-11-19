package com.orvils.common.message

import org.koin.core.annotation.Single
import java.util.Properties

@Single
class PropertiesHandler(private val path: String = "messages/messages.properties") {

    private val properties = Properties()
    private val loader = PropertiesHandler::class.java.getClassLoader()

    init {
        load()
    }

    private fun load() {
        try {
            loader.getResourceAsStream(path).use { propIs ->
                if (propIs == null) {
                    throw PropertiesHandlingException("The properties file cannot be found in the path '$path'")
                }

                properties.load(propIs)

                if (properties.isEmpty) {
                    throw PropertiesHandlingException("The properties file in the path '$path' is empty")
                }
            }
        } catch (e: Exception) {
            throw PropertiesHandlingException("Error loading properties", e)
        }
    }

    @Suppress("unchecked_cast")
    fun propertyKeys(): List<String> = properties.propertyNames().toList() as List<String>

    @Suppress("unchecked_cast")
    fun propertyValues(): List<String> = properties.values.toList() as List<String>

    fun propertyValue(key: String?): String {
        if (key.isNullOrBlank()) return ""
        return properties.getProperty(key) ?: key
    }

}