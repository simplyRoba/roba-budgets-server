package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.IncomeRepository
import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import de.simplyroba.suite.budgets.rest.error.NotFoundError
import de.simplyroba.suite.budgets.rest.model.Income
import de.simplyroba.suite.budgets.rest.model.IncomeCreate
import de.simplyroba.suite.budgets.rest.model.IncomeUpdate
import de.simplyroba.suite.budgets.service.converter.Converter
import java.time.LocalDate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IncomeService(
  private val incomeRepository: IncomeRepository,
  private val incomeEntityToIncomeConverter: Converter<IncomeEntity, Income>,
  private val incomeCreateToEntityConverter: Converter<IncomeCreate, IncomeEntity>
) {

  fun findAll(): Flux<Income> {
    return incomeRepository.findAll().map(incomeEntityToIncomeConverter::convert)
  }

  fun findAllBetweenDates(startDate: LocalDate, endDate: LocalDate): Flux<Income> {
    return incomeRepository
      .findAllByDueDateBetween(startDate, endDate)
      .map(incomeEntityToIncomeConverter::convert)
  }

  fun findById(id: Long): Mono<Income> {
    return incomeRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Income with id $id not found")))
      .map(incomeEntityToIncomeConverter::convert)
  }

  @Transactional
  fun createIncome(income: IncomeCreate): Mono<Income> {
    return incomeRepository
      .save(incomeCreateToEntityConverter.convert(income))
      .map(incomeEntityToIncomeConverter::convert)
  }

  @Transactional
  fun updateIncome(id: Long, incomeUpdate: IncomeUpdate): Mono<Income> {
    return incomeRepository
      .findById(id)
      .switchIfEmpty(Mono.error(NotFoundError("Income with id $id not found")))
      .map { existingIncome ->
        existingIncome.apply {
          title = incomeUpdate.title
          amountInCents = incomeUpdate.amountInCents
          dueDate = incomeUpdate.dueDate
        }
      }
      .flatMap(incomeRepository::save)
      .map(incomeEntityToIncomeConverter::convert)
  }

  @Transactional
  fun deleteIncome(id: Long): Mono<Void> {
    return incomeRepository.deleteById(id)
  }
}
