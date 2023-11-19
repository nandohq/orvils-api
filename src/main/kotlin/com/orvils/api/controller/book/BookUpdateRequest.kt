package com.orvils.api.controller.book

import com.orvils.api.common.validation.*
import com.orvils.api.controller.common.resource.BasePatchRequest
import com.orvils.common.`object`.Absent
import com.orvils.common.`object`.OptionalValued
import com.orvils.common.`object`.checkIf
import com.orvils.domain.model.EditionType

data class BookUpdateRequest(
    val title: OptionalValued<String> = Absent,
    val isbn: OptionalValued<String> = Absent,
    val summary: OptionalValued<String> = Absent,
    val editionType: OptionalValued<String> = Absent,
    val editionNumber: OptionalValued<String> = Absent,
    val authorId: OptionalValued<String> = Absent,
    val publisherId: OptionalValued<String> = Absent,
    val collectionId: OptionalValued<String> = Absent
) : BasePatchRequest {

    init {
        isbn.checkIf { isIsbn() }
        editionType.checkIf { isEnumeration(EditionType::class) }
        editionNumber.checkIf { isNumber(onlyPositive = true) }
        authorId.checkIf { isUUID() }
        publisherId.checkIf { isUUID() }
        collectionId.checkIf { isUUID() }
    }

}
