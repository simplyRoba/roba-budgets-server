package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.FixExpenseTemplateRepository
import de.simplyroba.suite.budgets.persistence.model.FixExpenseTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplate
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateCreate
import de.simplyroba.suite.budgets.rest.model.FixExpenseTemplateUpdate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import de.simplyroba.suite.budgets.service.converter.Converter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class FixExpenseTemplateService(
  private val fixExpenseTemplateRepository: FixExpenseTemplateRepository,
  private val fixExpenseTemplateEntityToDtoConverter:
    Converter<FixExpenseTemplateEntity, FixExpenseTemplate>,
  private val fixExpenseTemplateCreateToEntityConverter:
    Converter<FixExpenseTemplateCreate, FixExpenseTemplateEntity>,
  private val repeatIntervalToPersistenceEnumConverter:
    Converter<RepeatInterval, RepeatIntervalPersistenceEnum>,
) {

  fun findAll(): Flux<FixExpenseTemplate> {
    return fixExpenseTemplateRepository
      .findAll()
      .map(fixExpenseTemplateEntityToDtoConverter::convert)
  }

  fun findById(id: Long): Mono<FixExpenseTemplate> {
    return fixExpenseTemplateRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("FixExpenseTemplate with id $id not found")))
      .map(fixExpenseTemplateEntityToDtoConverter::convert)
  }

  @Transactional
  fun createFixExpenseTemplate(
    fixExpenseTemplate: FixExpenseTemplateCreate
  ): Mono<FixExpenseTemplate> {
    return fixExpenseTemplateRepository
      .save(fixExpenseTemplateCreateToEntityConverter.convert(fixExpenseTemplate))
      .map(fixExpenseTemplateEntityToDtoConverter::convert)
  }

  @Transactional
  fun updateFixExpenseTemplate(
    id: Long,
    fixExpenseTemplate: FixExpenseTemplateUpdate,
  ): Mono<FixExpenseTemplate> {
    return fixExpenseTemplateRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("FixExpenseTemplate with id $id not found")))
      .map { existingFixExpense ->
        existingFixExpense.apply {
          title = fixExpenseTemplate.title
          amountInCents = fixExpenseTemplate.amountInCents
          repeatInterval =
            repeatIntervalToPersistenceEnumConverter.convert(fixExpenseTemplate.repeatInterval)
          categoryId = fixExpenseTemplate.categoryId
        }
      }
      .flatMap { fixExpenseTemplateRepository.save(it) }
      .map(fixExpenseTemplateEntityToDtoConverter::convert)
  }

  @Transactional
  fun deleteFixExpenseTemplate(id: Long): Mono<Void> {
    return fixExpenseTemplateRepository.deleteById(id)
  }
}
