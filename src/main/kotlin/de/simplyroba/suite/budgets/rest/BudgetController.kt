package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.Budget
import de.simplyroba.suite.budgets.rest.model.BudgetCreate
import de.simplyroba.suite.budgets.rest.model.BudgetUpdate
import de.simplyroba.suite.budgets.service.BudgetService
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
@RequestMapping("api/v1/budget")
@CrossOrigin
class BudgetController(
  private val budgetService: BudgetService,
) {

  @GetMapping fun getBudgetList(): Flux<Budget> = budgetService.findAll()

  @GetMapping("/{id}")
  fun getBudgetById(
    @PathVariable id: Long,
  ): Mono<Budget> = budgetService.findById(id)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createBudget(
    @RequestBody budget: BudgetCreate,
  ): Mono<Budget> = budgetService.createBudget(budget)

  @PutMapping("/{id}")
  fun updateBudget(
    @PathVariable id: Long,
    @RequestBody budget: BudgetUpdate,
  ): Mono<Budget> = budgetService.updateBudget(id, budget)

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteBudget(
    @PathVariable id: Long,
  ): Mono<Void> = budgetService.deleteBudget(id)
}
