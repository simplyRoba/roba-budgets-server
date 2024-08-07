package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.persistence.Income
import de.simplyroba.suite.budgets.service.IncomeService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/income")
class IncomeController(private val incomeService: IncomeService) {

  @GetMapping()
  fun getIncomeList(): Flux<Income> {
    return incomeService.findAll().log()
  }

  @GetMapping("/{id}")
  fun getIncomeById(
    @PathVariable id: Long,
  ): Mono<Income> {
    return incomeService.findById(id).log()
  }

  @PostMapping()
  fun createIncome(
    @RequestBody income: Income,
  ): Mono<Income> {
    return incomeService.createIncome(income).log()
  }

  @PutMapping("/{id}")
  fun updateIncome(
    @RequestBody income: Income,
    @PathVariable id: Long,
  ): Mono<Income> {
    return incomeService.updateIncome(income, id).log()
  }

  @DeleteMapping("/{id}")
  fun deleteIncome(
    @PathVariable id: Long,
  ): Mono<Void> {
    return incomeService.deleteIncome(id).log()
  }
}
