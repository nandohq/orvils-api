package com.orvils.common.mapper

class MergeStrategy(
    private val ignorableFields: List<String> = emptyList(),
    private val namingStrategies: List<Pair<String, String>> = emptyList()
) {

    fun isIgnorable(key: String) = ignorableFields.contains(key)

    fun getStrategicKey(key: String) = namingStrategies.firstOrNull { it.first == key }?.second ?: key

    data class Builder(
        private var ignorableFields: MutableList<String> = mutableListOf(),
        private var namingStrategies: MutableList<Pair<String, String>> = mutableListOf()
    ) {

        fun ignoredFields(vararg fields: String) = apply {
            this.ignorableFields.addAll(fields.filter { it.isNotBlank() })
        }

        fun nameStrategy(from: String, to: String) = apply {
            this.namingStrategies(Pair(from, to))
        }

        fun namingStrategies(vararg strategies: Pair<String, String>) = apply {
            this.namingStrategies.addAll(strategies.filter { it.first.isNotBlank() && it.second.isNotBlank() })
        }

        fun build() = MergeStrategy(ignorableFields, namingStrategies)
    }
}