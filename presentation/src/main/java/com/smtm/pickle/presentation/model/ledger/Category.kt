package com.smtm.pickle.presentation.model.ledger

enum class Category(
    val label: String,
    // 실제 아이콘은 나중에 디자인 확정 후 변경
) {
    FOOD("식비"),
    TRANSPORT("교통비"),
    HOUSING("주거비"),
    SHOPPING("쇼핑"),
    MEDICAL("의료/건강"),
    EDUCATION("교육/자기계발"),
    LEISURE("여가/취미"),
    SAVING("저축/금융"),
    OTHER("기타")
}