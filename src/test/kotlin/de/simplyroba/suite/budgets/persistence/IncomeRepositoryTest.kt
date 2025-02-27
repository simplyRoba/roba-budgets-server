package de.simplyroba.suite.budgets.persistence

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import java.time.LocalDate
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import reactor.kotlin.test.test

class IncomeRepositoryTest : AbstractIntegrationTest() {

	@Autowired private lateinit var incomeRepository: IncomeRepository

	@Test
	fun `should include start and end date`() {

		val startDate = LocalDate.parse("2021-01-01")
		val betweenDate = LocalDate.parse("2021-01-02")
		val endDate = LocalDate.parse("2021-01-03")

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
