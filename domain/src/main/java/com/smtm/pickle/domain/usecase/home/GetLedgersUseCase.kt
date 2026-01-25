package com.smtm.pickle.domain.usecase.home

import com.smtm.pickle.domain.model.ledger.Category
import com.smtm.pickle.domain.model.ledger.LedgerEntry
import com.smtm.pickle.domain.model.ledger.LedgerId
import com.smtm.pickle.domain.model.ledger.LedgerType
import com.smtm.pickle.domain.model.ledger.Money
import com.smtm.pickle.domain.model.ledger.PaymentMethod
import java.time.LocalDate
import javax.inject.Inject

class GetLedgersUseCase @Inject constructor(

) {
    suspend operator fun invoke(): List<LedgerEntry> {
        return listOf(
            // 오늘
            LedgerEntry(
                id = LedgerId(1L),
                type = LedgerType.INCOME,
                amount = Money(2_500_000),
                category = Category.SAVING_FINANCE,
                description = "월급",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethod.BANK_TRANSFER
            ),
            LedgerEntry(
                id = LedgerId(2L),
                type = LedgerType.EXPENSE,
                amount = Money(12_000),
                category = Category.FOOD,
                description = "점심",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethod.CARD
            ),
            LedgerEntry(
                id = LedgerId(3L),
                type = LedgerType.EXPENSE,
                amount = Money(4_500),
                category = Category.FOOD,
                description = "커피",
                occurredOn = LocalDate.now(),
                paymentMethod = PaymentMethod.CARD
            ),

            // 어제
            LedgerEntry(
                id = LedgerId(4L),
                type = LedgerType.EXPENSE,
                amount = Money(35_000),
                category = Category.FOOD,
                description = "저녁 외식",
                occurredOn = LocalDate.now().minusDays(1),
                paymentMethod = PaymentMethod.CARD
            ),
            LedgerEntry(
                id = LedgerId(5L),
                type = LedgerType.EXPENSE,
                amount = Money(15_000),
                category = Category.TRANSPORT,
                description = "택시비",
                occurredOn = LocalDate.now().minusDays(1),
                paymentMethod = PaymentMethod.CARD
            ),

            // 2일 전
            LedgerEntry(
                id = LedgerId(6L),
                type = LedgerType.EXPENSE,
                amount = Money(17_000),
                category = Category.LEISURE_HOBBY,
                description = "넷플릭스",
                occurredOn = LocalDate.now().minusDays(2),
                paymentMethod = PaymentMethod.CARD
            ),
            LedgerEntry(
                id = LedgerId(7L),
                type = LedgerType.EXPENSE,
                amount = Money(45_000),
                category = Category.SHOPPING,
                description = "온라인 쇼핑",
                occurredOn = LocalDate.now().minusDays(2),
                paymentMethod = PaymentMethod.CARD
            ),

            // 3일 전
            LedgerEntry(
                id = LedgerId(8L),
                type = LedgerType.INCOME,
                amount = Money(100_000),
                category = Category.OTHER,
                description = "용돈",
                occurredOn = LocalDate.now().minusDays(3),
                paymentMethod = PaymentMethod.CASH
            ),
            LedgerEntry(
                id = LedgerId(9L),
                type = LedgerType.EXPENSE,
                amount = Money(25_000),
                category = Category.HEALTH_MEDICAL,
                description = "병원비",
                occurredOn = LocalDate.now().minusDays(3),
                paymentMethod = PaymentMethod.CARD
            ),

            // 5일 전
            LedgerEntry(
                id = LedgerId(10L),
                type = LedgerType.EXPENSE,
                amount = Money(55_000),
                category = Category.TRANSPORT,
                description = "지하철 정기권",
                occurredOn = LocalDate.now().minusDays(5),
                paymentMethod = PaymentMethod.CARD
            ),
            LedgerEntry(
                id = LedgerId(11L),
                type = LedgerType.EXPENSE,
                amount = Money(9_000),
                category = Category.FOOD,
                description = "점심",
                occurredOn = LocalDate.now().minusDays(5),
                paymentMethod = PaymentMethod.CASH
            ),

            // 7일 전
            LedgerEntry(
                id = LedgerId(12L),
                type = LedgerType.INCOME,
                amount = Money(200_000),
                category = Category.OTHER,
                description = "부수입",
                occurredOn = LocalDate.now().minusDays(7),
                paymentMethod = PaymentMethod.BANK_TRANSFER
            ),
            LedgerEntry(
                id = LedgerId(13L),
                type = LedgerType.EXPENSE,
                amount = Money(99_000),
                category = Category.EDUCATION_SELF_DEVELOPMENT,
                description = "온라인 강의",
                occurredOn = LocalDate.now().minusDays(7),
                paymentMethod = PaymentMethod.CARD
            ),

            // 10일 전
            LedgerEntry(
                id = LedgerId(14L),
                type = LedgerType.EXPENSE,
                amount = Money(150_000),
                category = Category.HOUSING,
                description = "관리비",
                occurredOn = LocalDate.now().minusDays(10),
                paymentMethod = PaymentMethod.BANK_TRANSFER
            ),
            LedgerEntry(
                id = LedgerId(15L),
                type = LedgerType.EXPENSE,
                amount = Money(45_000),
                category = Category.HOUSING,
                description = "전기세",
                occurredOn = LocalDate.now().minusDays(10),
                paymentMethod = PaymentMethod.BANK_TRANSFER
            ),

            // 14일 전
            LedgerEntry(
                id = LedgerId(16L),
                type = LedgerType.EXPENSE,
                amount = Money(80_000),
                category = Category.HEALTH_MEDICAL,
                description = "헬스장",
                occurredOn = LocalDate.now().minusDays(14),
                paymentMethod = PaymentMethod.CARD
            ),
            LedgerEntry(
                id = LedgerId(17L),
                type = LedgerType.EXPENSE,
                amount = Money(95_000),
                category = Category.FOOD,
                description = "마트 장보기",
                occurredOn = LocalDate.now().minusDays(14),
                paymentMethod = PaymentMethod.CARD
            ),

            // 20일 전
            LedgerEntry(
                id = LedgerId(18L),
                type = LedgerType.INCOME,
                amount = Money(15_000),
                category = Category.SAVING_FINANCE,
                description = "적금 이자",
                occurredOn = LocalDate.now().minusDays(20),
                paymentMethod = PaymentMethod.BANK_TRANSFER
            ),
            LedgerEntry(
                id = LedgerId(19L),
                type = LedgerType.EXPENSE,
                amount = Money(28_000),
                category = Category.LEISURE_HOBBY,
                description = "영화",
                occurredOn = LocalDate.now().minusDays(20),
                paymentMethod = PaymentMethod.CARD
            ),

            // 내일
            LedgerEntry(
                id = LedgerId(20L),
                type = LedgerType.EXPENSE,
                amount = Money(60_000),
                category = Category.FOOD,
                description = "예약 식당",
                occurredOn = LocalDate.now().plusDays(1),
                paymentMethod = PaymentMethod.CARD
            ),

            // 3일 후
            LedgerEntry(
                id = LedgerId(21L),
                type = LedgerType.EXPENSE,
                amount = Money(132_000),
                category = Category.LEISURE_HOBBY,
                description = "콘서트 티켓",
                occurredOn = LocalDate.now().plusDays(3),
                paymentMethod = PaymentMethod.CARD
            )
        )
    }
}