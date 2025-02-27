package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplate
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateCreate
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateUpdate
import de.simplyroba.suite.budgets.service.FixExpenseTemplateService
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
@RequestMapping("/api/v1/fix-expense-template")
@CrossOrigin
class FixExpenseTemplateController(
  private val fixExpenseTemplateService: FixExpenseTemplateService
) {

  @GetMapping
  fun getFixExpenseTemplateList(): Flux<FixExpenseTemplate> = fixExpenseTemplateService.findAll()

  @GetMapping("/{id}")
  fun getFixExpenseTemplateById(@PathVariable id: Long): Mono<FixExpenseTemplate> =
    fixExpenseTemplateService.findById(id)

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  fun createFixExpenseTemplate(
    @RequestBody fixExpenseTemplate: FixExpenseTemplateCreate
  ): Mono<FixExpenseTemplate> =
    fixExpenseTemplateService.createFixExpenseTemplate(fixExpenseTemplate)

  @PutMapping("/{id}")
  fun updateFixExpenseTemplate(
    @PathVariable id: Long,
    @RequestBody fixExpenseTemplate: FixExpenseTemplateUpdate,
  ): Mono<FixExpenseTemplate> =
    fixExpenseTemplateService.updateFixExpenseTemplate(id, fixExpenseTemplate)

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  fun deleteFixExpenseTemplate(@PathVariable id: Long): Mono<Void> =
    fixExpenseTemplateService.deleteFixExpenseTemplate(id)
}
