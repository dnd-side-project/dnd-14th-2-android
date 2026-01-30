package com.smtm.pickle.presentation.home.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.smtm.pickle.presentation.R

sealed class CategoryUi(
    @StringRes val stringResId: Int,
    @DrawableRes val activatedIconResId: Int,
    @DrawableRes val inactivatedIconResId: Int,
) {
    data object Food : CategoryUi(
        stringResId = R.string.ledger_category_food,
        activatedIconResId = R.drawable.ic_ledger_category_food_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_food_inactivated,
    )

    data object Transport : CategoryUi(
        stringResId = R.string.ledger_category_transport,
        activatedIconResId = R.drawable.ic_ledger_category_transport_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_transport_inactivated,
    )

    data object Housing : CategoryUi(
        stringResId = R.string.ledger_category_housing,
        activatedIconResId = R.drawable.ic_ledger_category_housing_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_housing_inactivated,
    )

    data object Shopping : CategoryUi(
        stringResId = R.string.ledger_category_shopping,
        activatedIconResId = R.drawable.ic_ledger_category_shopping_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_shopping_inactivated,
    )

    data object HealthMedical : CategoryUi(
        stringResId = R.string.ledger_category_health_medical,
        activatedIconResId = R.drawable.ic_ledger_category_health_medical_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_health_medical_inactivated,
    )

    data object EducationSelfDevelopment : CategoryUi(
        stringResId = R.string.ledger_category_education_self_development,
        activatedIconResId = R.drawable.ic_ledger_category_education_self_development_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_education_self_development_inactivated,
    )

    data object LeisureHobby : CategoryUi(
        stringResId = R.string.ledger_category_leisure_hobby,
        activatedIconResId = R.drawable.ic_ledger_category_leisure_hoby_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_leisure_hoby_inactivated,
    )

    data object SavingFinance : CategoryUi(
        stringResId = R.string.ledger_category_saving_finance,
        activatedIconResId = R.drawable.ic_ledger_category_saving_finance_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_saving_finance_inactivated,
    )

    data object Salary : CategoryUi(
        stringResId = R.string.ledger_category_salary,
        activatedIconResId = R.drawable.ic_ledger_category_salary_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_salary_inactivated,
    )

    data object SideIncome : CategoryUi(
        stringResId = R.string.ledger_category_side_income,
        activatedIconResId = R.drawable.ic_ledger_category_side_income_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_side_income_inactivated,
    )

    data object Bonus : CategoryUi(
        stringResId = R.string.ledger_category_bonus,
        activatedIconResId = R.drawable.ic_ledger_category_bonus_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_bonus_inactivated,
    )

    data object Allowance : CategoryUi(
        stringResId = R.string.ledger_category_allowance,
        activatedIconResId = R.drawable.ic_ledger_category_allowance_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_allowance_inactivated,
    )

    data object PartTimeIncome : CategoryUi(
        stringResId = R.string.ledger_category_part_time_income,
        activatedIconResId = R.drawable.ic_ledger_category_part_time_income_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_part_time_income_inactivated,
    )

    data object FinancialIncome : CategoryUi(
        stringResId = R.string.ledger_category_financial_income,
        activatedIconResId = R.drawable.ic_ledger_category_financial_income_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_financial_income_inactivated,
    )

    data object SplitBill : CategoryUi(
        stringResId = R.string.ledger_category_split_bill,
        activatedIconResId = R.drawable.ic_ledger_category_split_bill_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_split_bill_inactivated,
    )

    data object Transfer : CategoryUi(
        stringResId = R.string.ledger_category_transfer,
        activatedIconResId = R.drawable.ic_ledger_category_transfer_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_transfer_inactivated,
    )

    data object Other : CategoryUi(
        stringResId = R.string.ledger_category_other,
        activatedIconResId = R.drawable.ic_ledger_category_other_activated,
        inactivatedIconResId = R.drawable.ic_ledger_category_other_inactivated,
    )
}