package com.orvils.api.common.validation

import org.valiktor.Constraint

interface CustomConstraint : Constraint {

    val field: String get() = ""
    override val messageBundle: String get() = "messages/messages"

}