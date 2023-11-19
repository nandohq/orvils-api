package com.orvils.infrastructure.repository

import com.orvils.config.DBManager
import com.orvils.infrastructure.repository.resource.EntityFilter
import com.orvils.infrastructure.repository.resource.pagination.Page
import org.ktorm.database.Database
import org.ktorm.dsl.AliasRemover
import org.ktorm.dsl.insert
import org.ktorm.entity.Entity
import org.ktorm.expression.ArgumentExpression
import org.ktorm.expression.ColumnAssignmentExpression
import org.ktorm.expression.InsertExpression
import org.ktorm.schema.ColumnDeclaring
import org.ktorm.schema.Table
import org.ktorm.support.postgresql.InsertOrUpdateStatementBuilder
import java.awt.print.Pageable

open class RepositoryBaseImpl<I, E : Entity<E>>(
    protected val table: Table<E>,
    protected val db: Database = DBManager.db()
) : Repository<I, E> {

    override fun persist(model: E): E {
        val expression = db.dialect.createExpressionVisitor(AliasRemover).visit(
            InsertExpression(table.asExpression(), builder.assignments)
        )


        table.columns.forEach { column ->
            val teste = InsertOrUpdateStatementBuilder()
            val value =  model.properties[column.name]
            teste.set(column, ArgumentExpression(value, column.sqlType))
            val expression = ColumnAssignmentExpression(column.asExpression(), ArgumentExpression(value, column.sqlType))
        }

        //db.executeUpdate()
        // ColumnAssignmentExpression(column.asExpression(), column.wrapArgument(value)
    }

    override fun update(model: E): E {
        TODO("Not yet implemented")
    }

    override fun findBy(id: I): E? {
        TODO("Not yet implemented")
    }

    override fun findWhere(condition: () -> ColumnDeclaring<Boolean>): E {
        TODO("Not yet implemented")
    }

    override fun filter(filter: EntityFilter, pageable: Pageable): Page<E> {
        TODO("Not yet implemented")
    }


}
