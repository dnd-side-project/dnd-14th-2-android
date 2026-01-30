package com.smtm.pickle.data.mapper

import com.smtm.pickle.data.source.local.database.entity.LedgerEntity
import com.smtm.pickle.domain.model.ledger.LedgerCategory
import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.Money
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import java.time.LocalDate

fun LedgerEntity.toDomain(): LedgerEntry = LedgerEntry(
    id = LedgerId(id),
    type = LedgerType.valueOf(type),
    amount = Money(amount),
    category = LedgerCategory.valueOf(category),
    description = description ?: category,
    occurredOn = LocalDate.ofEpochDay(occurredOn),
    paymentMethod = PaymentMethod.valueOf(paymentMethod),
    memo = memo
)

fun LedgerEntry.toEntity(): LedgerEntity = LedgerEntity(
    id = id.value,
    type = type.name,
    amount = amount.value,
    category = category.name,
    description = description,
    occurredOn = occurredOn.toEpochDay(),
    paymentMethod = paymentMethod.name,
    memo = memo
)