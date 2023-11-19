package com.orvils.api.common.validation

import com.orvils.common.extensions.toSnakeCase
import com.orvils.common.`object`.Present
import kotlin.reflect.KProperty1

class ValidatorHelper {

    companion object {

        fun <E, T> getValidatedFieldName(obj: E, property: KProperty1<E, T?>): String {
            return if (obj is Present<*>) (obj as Present<*>).field else property.name.toSnakeCase()
        }

    }

}