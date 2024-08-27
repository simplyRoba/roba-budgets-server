package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.IncomeTemplate
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateCreate
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateUpdate
import de.simplyroba.suite.budgets.service.IncomeTemplateService
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
@RequestMapping("/api/v1/income-template")
@CrossOrigin
class IncomeTemplateController(private val incomeTemplateService: IncomeTemplateService) {

  @GetMapping fun getIncomeTemplateList(): Flux<IncomeTemplate> = incomeTemplateService.findAll()

  @GetMapping("/{id}")
  fun getIncomeTemplateById(@PathVariable id: Long): Mono<IncomeTemplate> =
    incomeTemplateService.findById(id)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createIncomeTemplate(
    @RequestBody incomeTemplate: IncomeTemplateCreate
  ): Mono<IncomeTemplate> = incomeTemplateService.createIncomeTemplate(incomeTemplate)

  @PutMapping("/{id}")
  fun updateIncomeTemplate(
    @PathVariable id: Long,
    @RequestBody incomeTemplate: IncomeTemplateUpdate
  ): Mono<IncomeTemplate> = incomeTemplateService.updateIncomeTemplate(id, incomeTemplate)

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteIncomeTemplate(@PathVariable id: Long): Mono<Void> =
    incomeTemplateService.deleteIncomeTemplate(id)
}
