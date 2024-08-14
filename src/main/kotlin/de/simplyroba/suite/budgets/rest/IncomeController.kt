package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.Income
import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import de.simplyroba.suite.budgets.rest.model.IncomeUpdate
import de.simplyroba.suite.budgets.service.IncomeService
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
@RequestMapping("/api/v1/income")
@CrossOrigin
class IncomeController(private val incomeService: IncomeService) {

  @GetMapping
  fun getIncomeList(
    @RequestParam(required = false) startDate: LocalDate?,
    @RequestParam(required = false) endDate: LocalDate?,
  ): Flux<Income> =
    if (startDate != null && endDate != null) {
      incomeService.findAllBetweenDates(startDate, endDate)
    } else {
      incomeService.findAll()
    }

  @GetMapping("/{id}")
  fun getIncomeById(
    @PathVariable id: Long,
  ): Mono<Income> = incomeService.findById(id)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createIncome(
    @RequestBody income: IncomeCreate,
  ): Mono<Income> = incomeService.createIncome(income)

  @PutMapping("/{id}")
  fun updateIncome(
    @PathVariable id: Long,
    @RequestBody income: IncomeUpdate,
  ): Mono<Income> = incomeService.updateIncome(id, income)

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteIncome(
    @PathVariable id: Long,
  ): Mono<Void> = incomeService.deleteIncome(id)
}
