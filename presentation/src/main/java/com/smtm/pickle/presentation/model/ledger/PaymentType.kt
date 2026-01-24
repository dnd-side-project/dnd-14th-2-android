package com.smtm.pickle.presentation.model.ledger

enum class PaymentType(val label: String) {
    CREDIT_CARD("신용카드"),
    CASH("현금"),
    DEBIT_CARD("체크카드"),
    BANK_TRANSFER("계좌이체")
}
