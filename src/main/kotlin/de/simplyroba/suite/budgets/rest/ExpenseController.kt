package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.Expense
import de.simplyroba.suite.budgets.rest.model.ExpenseCreate
import de.simplyroba.suite.budgets.rest.model.ExpenseType
import de.simplyroba.suite.budgets.service.ExpenseService
import java.time.LocalDate
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/expense")
@CrossOrigin
class ExpenseController(private val expenseService: ExpenseService) {

  @GetMapping("/type/{type}/year/{year}/month/{month}")
  fun getExpenseListByType(
    @PathVariable type: ExpenseType,
    @PathVariable year: Int,
    @PathVariable month: Int,
  ): Flux<Expense> = expenseService.findAllByTypeYearAndMonth(type, year, month)

  @GetMapping("/category/{categoryId}")
  fun getExpenseListByCategory(
    @PathVariable categoryId: Long,
    @RequestParam(required = false) startDate: LocalDate?,
    @RequestParam(required = false) endDate: LocalDate?,
  ): Flux<Expense> =
    if (startDate != null && endDate != null) {
      expenseService.findAllCategoryBetweenDates(categoryId, startDate, endDate)
    } else {
      expenseService.findAllCategory(categoryId)
    }

  @GetMapping("/budget/{budgetId}")
  fun getExpenseListByBudget(
    @PathVariable budgetId: Long,
    @RequestParam(required = false) startDate: LocalDate?,
    @RequestParam(required = false) endDate: LocalDate?,
  ): Flux<Expense> =
    if (startDate != null && endDate != null) {
      expenseService.findAllByBudgetBetweenDates(budgetId, startDate, endDate)
    } else {
      expenseService.findAllByBudget(budgetId)
    }

  @GetMapping("/{id}/type/{type}")
  fun getExpenseById(@PathVariable id: Long, @PathVariable type: ExpenseType): Mono<Expense> =
    expenseService.findByIdAndType(id, type)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createExpense(@RequestBody expense: ExpenseCreate): Mono<Expense> =
    expenseService.createExpense(expense)

  @PutMapping("/{id}")
  fun updateExpense(@PathVariable id: Long, @RequestBody expense: ExpenseCreate): Mono<Expense> =
    expenseService.updateExpense(id, expense)

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteExpense(@PathVariable id: Long): Mono<Void> = expenseService.deleteExpense(id)
}
