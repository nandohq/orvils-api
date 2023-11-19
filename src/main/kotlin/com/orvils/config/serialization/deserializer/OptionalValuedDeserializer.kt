package com.orvils.config.serialization.deserializer

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.orvils.common.`object`.*

class OptionalValuedDeserializer : JsonDeserializer<OptionalValued<*>>() {

    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): OptionalValued<*> {
        return Present(p.currentName, p.valueAsString)
    }

    override fun getNullValue(ctxt: DeserializationContext): OptionalValued<*> {
        return Nullable(ctxt.parser.currentName)
    }

    override fun getAbsentValue(ctxt: DeserializationContext): Any {
        return Absent
    }

}
