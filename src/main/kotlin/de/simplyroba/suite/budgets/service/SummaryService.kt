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
import reactor.core.scheduler.Schedulers
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
					.findAllByDueDateBetween(yearMonth.atDay(1), yearMonth.atEndOfMonth())
					.collectList()
					.subscribeOn(Schedulers.boundedElastic()),
				expenseRepository
					.findAllByDueDateBetween(yearMonth.atDay(1), yearMonth.atEndOfMonth())
					.collectList()
					.subscribeOn(Schedulers.boundedElastic()),
			)
			.map { (incomes, expenses) ->
				MonthlySummary(
					month = month,
					year = year,
					totalIncomeInCents = incomes.sumOf { it.amountInCents },
					totalFixExpensesInCents = expenses.filter { it.type == FIX }.sumOf { it.amountInCents },
					totalFlexExpensesInCents = expenses.filter { it.type == FLEX }.sumOf { it.amountInCents },
				)
			}
	}

	fun getBudgetSummary(): Mono<List<BudgetSummary>> {
		return Mono.zip(
				budgetRepository.findAll().collectList().subscribeOn(Schedulers.boundedElastic()),
				budgetExpenseRepository.findAll().collectList().subscribeOn(Schedulers.boundedElastic()),
			)
			.map { (budgets, expenses) ->
				budgets.map { budget ->
					BudgetSummary(
						id = budget.id,
						name = budget.name,
						totalSavedAmountInCents = budget.totalSavedAmountInCents,
						totalExpensesInCents =
							expenses.filter { it.budgetId == budget.id }.sumOf { it.amountInCents },
					)
				}
			}
	}
}
