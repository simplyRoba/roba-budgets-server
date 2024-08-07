package de.simplyroba.suite.budgets.service

import de.simplyroba.suite.budgets.persistence.Income
import de.simplyroba.suite.budgets.persistence.IncomeRepository
import java.time.OffsetDateTime
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class IncomeService(private val incomeRepository: IncomeRepository) {

  fun findAll(): Flux<Income> {
    return incomeRepository.findAll()
  }

  fun findAllBetweenDates(startDate: OffsetDateTime, endDate: OffsetDateTime): Flux<Income> {
    return incomeRepository.findAllByDueDateBetween(startDate, endDate)
  }

  fun findById(id: Long): Mono<Income> {
    return incomeRepository.findById(id)
    // TODO try json problem later
    // https://docs.spring.io/spring-boot/reference/web/reactive.html#web.reactive.webflux.error-handling
    // .switchIfEmpty(Mono.error(IllegalArgumentException("Income with id $id not found")))
  }

  fun createIncome(income: Income): Mono<Income> {
    return incomeRepository.save(income)
  }

  fun updateIncome(newIncome: Income, id: Long): Mono<Income> {
    return incomeRepository
      .findById(id)
      // TODO try json problem later
      // https://docs.spring.io/spring-boot/reference/web/reactive.html#web.reactive.webflux.error-handling
      // .switchIfEmpty(Mono.error(IllegalArgumentException("Income with id $id not found")))
      .map { existingIncome ->
        existingIncome.apply {
          amountInCents = newIncome.amountInCents
          dueDate = newIncome.dueDate
        }
      }
      .flatMap { incomeRepository.save(it) }
  }

  fun deleteIncome(id: Long): Mono<Void> {
    return incomeRepository.deleteById(id)
  }
}
