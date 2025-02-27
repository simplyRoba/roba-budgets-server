package de.simplyroba.suite.budgets.rest.model

import java.time.LocalDate

data class Income(val id: Long, val title: String, val amountInCents: Int, val dueDate: LocalDate)

data class IncomeCreate(val title: String, val amountInCents: Int, val dueDate: LocalDate)

typealias IncomeUpdate = IncomeCreate
