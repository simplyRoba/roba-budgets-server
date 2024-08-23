package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.BudgetExpenseRepository
import de.simplyroba.suite.budgets.persistence.BudgetRepository
import de.simplyroba.suite.budgets.persistence.ExpenseRepository
import de.simplyroba.suite.budgets.persistence.IncomeRepository
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum.FIX
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum.FLEX
import de.simplyroba.suite.budgets.rest.model.BudgetSummary
import de.simplyroba.suite.budgets.rest.model.MonthlySummary
import java.time.YearMonth
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.util.function.*

@Service
class SummaryService(
  private val incomeRepository: IncomeRepository,
  private val expenseRepository: ExpenseRepository,
  private val budgetRepository: BudgetRepository,
  private val budgetExpenseRepository: BudgetExpenseRepository,
) {

  fun getSummaryForYearAndMonth(year: Int, month: Int): Mono<MonthlySummary> {
    val yearMonth = YearMonth.of(year, month)
    return Mono.zip(
        incomeRepository
          .findAllByDueDateBetween(
            yearMonth.atDay(1),
            yearMonth.atEndOfMonth(),
          )
          .collectList(),
        expenseRepository
          .findAllByDueDateBetween(
            yearMonth.atDay(1),
            yearMonth.atEndOfMonth(),
          )
          .collectList(),
        getBudgets(),
      )
      .map { (incomes, expenses, budgets) ->
        MonthlySummary(
          month = month,
          year = year,
          totalIncomeInCents = incomes.sumOf { it.amountInCents },
          totalFixExpensesInCents = expenses.filter { it.type == FIX }.sumOf { it.amountInCents },
          totalFlexExpensesInCents = expenses.filter { it.type == FLEX }.sumOf { it.amountInCents },
          budgets = budgets
        )
      }
  }

  private fun getBudgets(): Mono<List<BudgetSummary>> {
    return Mono.zip(
        budgetRepository.findAll().collectList(),
        budgetExpenseRepository.findAll().collectList(),
      )
      .map { (budgets, expenses) ->
        budgets.map { budget ->
          BudgetSummary(
            name = budget.name,
            remainingInCents = budget.totalSavedAmountInCents,
            totalExpensesInCents =
              expenses.filter { it.budgetId == budget.id }.sumOf { it.amountInCents },
          )
        }
      }
  }
}