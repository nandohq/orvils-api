package com.orvils.infrastructure.repository

import com.orvils.infrastructure.repository.resource.EntityFilter
import com.orvils.infrastructure.repository.resource.pagination.Page
import org.ktorm.entity.Entity
import org.ktorm.schema.ColumnDeclaring
import java.awt.print.Pageable

interface Repository<I, E : Entity<E>> {

    fun persist(model: E) : E

    fun update(model: E) : E

    fun findBy(id: I) : E?

    fun findWhere(condition: () -> ColumnDeclaring<Boolean>) : E

    fun filter(filter: EntityFilter, pageable: Pageable) : Page<E>

}