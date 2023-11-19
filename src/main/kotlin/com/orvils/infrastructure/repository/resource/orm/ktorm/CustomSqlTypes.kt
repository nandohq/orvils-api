package com.orvils.infrastructure.repository.resource.orm.ktorm

import org.ktorm.schema.BaseTable
import org.ktorm.schema.Column
import org.ktorm.schema.SqlType
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.Timestamp
import java.sql.Types
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun BaseTable<*>.zonedDatetime(name: String): Column<OffsetDateTime> {
    return registerColumn(name, OffsetDateTimeType)
}

object OffsetDateTimeType : SqlType<OffsetDateTime>(Types.TIMESTAMP, "datetime") {

    override fun doGetResult(rs: ResultSet, index: Int): OffsetDateTime? {
        return rs.getTimestamp(index)?.toLocalDateTime()?.atOffset(ZoneOffset.UTC)
    }

    override fun doSetParameter(ps: PreparedStatement, index: Int, parameter: OffsetDateTime) {
        val converted = parameter.atZoneSameInstant(ZoneOffset.UTC).toLocalDateTime()
        ps.setTimestamp(index, Timestamp.valueOf(converted))
    }

}