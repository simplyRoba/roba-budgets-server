package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import java.time.OffsetDateTime
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class IncomeEntityRepositoryTest : AbstractIntegrationTest() {

  @Autowired private lateinit var incomeRepository: IncomeRepository

  @Test
  fun `should include start and end date`() {

    val startDate = OffsetDateTime.parse("2021-01-01T00:00:00Z")
    val betweenDate = OffsetDateTime.parse("2021-01-01T12:00:00Z")
    val endDate = OffsetDateTime.parse("2021-01-02T00:00:00Z")

    val incomeEqualStartDate =
      IncomeEntity(title = "Income 1", amountInCents = 1000, dueDate = startDate)
    val incomeBetweenDate =
      IncomeEntity(title = "Income 2", amountInCents = 1000, dueDate = betweenDate)
    val incomeEqualEndDate =
      IncomeEntity(title = "Income 3", amountInCents = 1000, dueDate = endDate)

    incomeRepository
      .saveAll(listOf(incomeEqualStartDate, incomeBetweenDate, incomeEqualEndDate))
      .blockLast()

    incomeRepository
      .findAllByDueDateBetween(startDate, endDate)
      .test()
      .expectNextCount(3)
      .verifyComplete()
  }
}
