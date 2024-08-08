package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.Income
import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import de.simplyroba.suite.budgets.rest.model.IncomeUpdate
import de.simplyroba.suite.budgets.service.IncomeService
import java.time.OffsetDateTime
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/v1/income")
class IncomeController(private val incomeService: IncomeService) {

  @GetMapping()
  fun getIncomeList(
    @RequestParam(required = false) startDate: OffsetDateTime?,
    @RequestParam(required = false) endDate: OffsetDateTime?,
  ): Flux<Income> {
    return if (startDate == null || endDate == null) {
      incomeService.findAll().log()
    } else {
      incomeService.findAllBetweenDates(startDate, endDate)
    }
  }

  @GetMapping("/{id}")
  fun getIncomeById(
    @PathVariable id: Long,
  ): Mono<Income> {
    return incomeService.findById(id)
  }

  @PostMapping()
  fun createIncome(
    @RequestBody income: IncomeCreate,
  ): Mono<Income> {
    return incomeService.createIncome(income)
  }

  @PutMapping("/{id}")
  fun updateIncome(
    @RequestBody income: IncomeUpdate,
    @PathVariable id: Long,
  ): Mono<Income> {
    return incomeService.updateIncome(income, id)
  }

  @DeleteMapping("/{id}")
  fun deleteIncome(
    @PathVariable id: Long,
  ): Mono<Void> {
    return incomeService.deleteIncome(id)
  }
}
