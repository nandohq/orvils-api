package com.orvils.common.mapper

import com.orvils.config.serialization.JsonParser
import kotlin.reflect.KClass

class CommonMapper {

    companion object {
        private val MAPPER = JsonParser.instance

        fun <T : Any> convertTo(value: Any, objectType: KClass<T>): T {
            return MAPPER.readValue(stringify(value), objectType.java)
        }

        fun stringify(value: Any): String {
            return if (value is String) value else MAPPER.writeValueAsString(value)
        }

        fun toBytes(value: Any): ByteArray {
            return MAPPER.writeValueAsBytes(value)
        }

        fun <T: Any> merge(source: Any, target: Any, returnType: KClass<T>, mergeStrategy: MergeStrategy = MergeStrategy()): T {
            val mappedSource = toMap(source)
            val mappedTarget = toMap(target).toMutableMap()

            mappedSource.forEach { (key, value) ->
                val correctKey = mergeStrategy.getStrategicKey(key)

                if (mergeStrategy.isIgnorable(correctKey) || mappedTarget.contains(correctKey).not()) {
                    return@forEach
                }

                if (correctKey.contains(".").not()) {
                    mappedTarget[correctKey] = value
                    return@forEach
                }

                val paths = correctKey.split(".")
                paths.forEachIndexed { index, path ->
                    val nextIndex = index + 1
                    val valueTime = if (nextIndex <= paths.size) paths[nextIndex] else value
                    mappedTarget[path] = valueTime
                }

            }

            return convertTo(mappedTarget, returnType)
        }

        @Suppress("unchecked_cast")
        private fun toMap(value: Any): Map<String, Any?> {
            if (value is Map<*, *>) {
                return value as Map<String, Any?>
            }

            return convertTo(value, Map::class) as Map<String, Any?>
        }
    }

}