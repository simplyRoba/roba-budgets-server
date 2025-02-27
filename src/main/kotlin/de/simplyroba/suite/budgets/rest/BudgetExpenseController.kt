package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.BudgetExpense
import de.simplyroba.suite.budgets.rest.model.BudgetExpenseCreate
import de.simplyroba.suite.budgets.rest.model.BudgetExpenseUpdate
import de.simplyroba.suite.budgets.service.BudgetExpenseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/budget-expense")
@CrossOrigin
class BudgetExpenseController(private val budgetExpenseService: BudgetExpenseService) {

	@GetMapping fun getBudgetExpenseList(): Flux<BudgetExpense> = budgetExpenseService.findAll()

	@GetMapping("/{id}")
	fun getBudgetExpenseById(@PathVariable id: Long): Mono<BudgetExpense> =
		budgetExpenseService.findById(id)

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createBudgetExpense(@RequestBody budgetExpense: BudgetExpenseCreate): Mono<BudgetExpense> =
		budgetExpenseService.createBudgetExpense(budgetExpense)

	@PutMapping("/{id}")
	fun updateBudgetExpense(
		@PathVariable id: Long,
		@RequestBody budgetExpense: BudgetExpenseUpdate,
	): Mono<BudgetExpense> = budgetExpenseService.updateBudgetExpense(id, budgetExpense)

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	fun deleteBudgetExpense(@PathVariable id: Long): Mono<Void> =
		budgetExpenseService.deleteBudgetExpense(id)
}
