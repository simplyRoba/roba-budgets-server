package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.IncomeTemplateRepository
import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.IncomeTemplate
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateCreate
import de.simplyroba.suite.budgets.rest.model.IncomeTemplateUpdate
import de.simplyroba.suite.budgets.rest.model.RepeatInterval
import de.simplyroba.suite.budgets.service.converter.Converter
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IncomeTemplateService(
  private val incomeTemplateRepository: IncomeTemplateRepository,
  private val incomeTemplateEntityToDtoConverter: Converter<IncomeTemplateEntity, IncomeTemplate>,
  private val incomeTemplateCreateToEntityConverter:
    Converter<IncomeTemplateCreate, IncomeTemplateEntity>,
  private val repeatIntervalToPersistenceEnumConverter:
    Converter<RepeatInterval, RepeatIntervalPersistenceEnum>,
) {

  fun findAll(): Flux<IncomeTemplate> {
    return incomeTemplateRepository.findAll().map(incomeTemplateEntityToDtoConverter::convert)
  }

  fun findById(id: Long): Mono<IncomeTemplate> {
    return incomeTemplateRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("IncomeTemplate with id $id not found")))
      .map(incomeTemplateEntityToDtoConverter::convert)
  }

  @Transactional
  fun createIncomeTemplate(incomeTemplate: IncomeTemplateCreate): Mono<IncomeTemplate> {
    return incomeTemplateRepository
      .save(incomeTemplateCreateToEntityConverter.convert(incomeTemplate))
      .map(incomeTemplateEntityToDtoConverter::convert)
  }

  @Transactional
  fun updateIncomeTemplate(
    id: Long,
    incomeTemplateUpdate: IncomeTemplateUpdate
  ): Mono<IncomeTemplate> {
    return incomeTemplateRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("IncomeTemplate with id $id not found")))
      .map { existingIncome ->
        existingIncome.apply {
          title = incomeTemplateUpdate.title
          amountInCents = incomeTemplateUpdate.amountInCents
          repeatInterval =
            repeatIntervalToPersistenceEnumConverter.convert(incomeTemplateUpdate.repeatInterval)
        }
      }
      .flatMap(incomeTemplateRepository::save)
      .map(incomeTemplateEntityToDtoConverter::convert)
  }

  @Transactional
  fun deleteIncomeTemplate(id: Long): Mono<Void> {
    return incomeTemplateRepository.deleteById(id)
  }
}
