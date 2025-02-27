package de.simplyroba.suite.budgets

import de.simplyroba.suite.budgets.persistence.BudgetExpenseRepository
import de.simplyroba.suite.budgets.persistence.BudgetRepository
import de.simplyroba.suite.budgets.persistence.CategoryRepository
import de.simplyroba.suite.budgets.persistence.ExpenseRepository
import de.simplyroba.suite.budgets.persistence.FixExpenseTemplateRepository
import de.simplyroba.suite.budgets.persistence.IncomeRepository
import de.simplyroba.suite.budgets.persistence.IncomeTemplateRepository
import de.simplyroba.suite.budgets.persistence.model.BudgetEntity
import de.simplyroba.suite.budgets.persistence.model.BudgetExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import de.simplyroba.suite.budgets.persistence.model.FixExpenseTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import de.simplyroba.suite.budgets.persistence.model.IncomeTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import io.r2dbc.spi.ConnectionFactory
import java.time.LocalDate
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.r2dbc.connection.init.ScriptUtils
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@Import(TestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class AbstractIntegrationTest {

  @Autowired lateinit var webTestClient: WebTestClient

  @Autowired private lateinit var connectionFactory: ConnectionFactory

  @Autowired private lateinit var incomeRepository: IncomeRepository
  @Autowired private lateinit var incomeTemplateRepository: IncomeTemplateRepository
  @Autowired private lateinit var expenseRepository: ExpenseRepository
  @Autowired private lateinit var fixExpenseTemplateRepository: FixExpenseTemplateRepository
  @Autowired private lateinit var categoryRepository: CategoryRepository
  @Autowired private lateinit var budgetRepository: BudgetRepository
  @Autowired private lateinit var budgetExpenseRepository: BudgetExpenseRepository

  @AfterEach
  fun cleanUp(@Value("classpath:clean-up.sql") sqlScript: Resource) {
    // execute clean-up.sql script after each test as there is no @Sql annotation in R2DBC
    Mono.usingWhen(
        connectionFactory.create(),
        { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) },
        { connection -> connection.close() },
      )
      .block()
  }

  fun createIncome(
    title: String = "Income",
    amountInCents: Int = 999,
    dueDate: LocalDate = LocalDate.now(),
  ): IncomeEntity {
    return incomeRepository
      .save(IncomeEntity(title = title, amountInCents = amountInCents, dueDate = dueDate))
      .log("create income $title")
      .blockOptional()
      .get()
  }

  fun createIncomeTemplate(
    title: String = "Income Template",
    amountInCents: Int = 999,
    repeatInterval: RepeatIntervalPersistenceEnum = RepeatIntervalPersistenceEnum.MONTHLY,
  ): IncomeTemplateEntity {
    return incomeTemplateRepository
      .save(
        IncomeTemplateEntity(
          title = title,
          amountInCents = amountInCents,
          repeatInterval = repeatInterval,
        )
      )
      .log("create income template $title")
      .blockOptional()
      .get()
  }

  fun createExpense(
    title: String = "Expense",
    amountInCents: Int = 999,
    dueDate: LocalDate = LocalDate.now(),
    type: ExpenseTypePersistenceEnum = ExpenseTypePersistenceEnum.FLEX,
    categoryId: Long,
    budgetId: Long? = null,
  ): ExpenseEntity {
    return expenseRepository
      .save(
        ExpenseEntity(
          title = title,
          amountInCents = amountInCents,
          dueDate = dueDate,
          type = type,
          categoryId = categoryId,
          budgetId = budgetId,
        )
      )
      .log("create expense $title")
      .blockOptional()
      .get()
  }

  fun createFixExpenseTemplate(
    title: String = "Fix Expense Template",
    amountInCents: Int = 999,
    repeatInterval: RepeatIntervalPersistenceEnum = RepeatIntervalPersistenceEnum.MONTHLY,
    categoryId: Long,
  ): FixExpenseTemplateEntity {
    return fixExpenseTemplateRepository
      .save(
        FixExpenseTemplateEntity(
          title = title,
          amountInCents = amountInCents,
          repeatInterval = repeatInterval,
          categoryId = categoryId,
        )
      )
      .log("create fix expense template $title")
      .blockOptional()
      .get()
  }

  fun createCategory(
    name: String = "Category",
    disabled: Boolean = false,
    parentCategoryId: Long? = null,
  ): CategoryEntity {
    return categoryRepository
      .save(CategoryEntity(name = name, disabled = disabled, parentCategoryId = parentCategoryId))
      .log("create category $name")
      .blockOptional()
      .get()
  }

  fun createBudget(
    name: String = "Budget",
    monthlySavingAmountInCents: Int = 10000,
    totalSavedAmountInCents: Int = 100000,
    categoryId: Long,
  ): BudgetEntity {
    return budgetRepository
      .save(
        BudgetEntity(
          name = name,
          monthlySavingAmountInCents = monthlySavingAmountInCents,
          totalSavedAmountInCents = totalSavedAmountInCents,
          categoryId = categoryId,
        )
      )
      .log("create budget $name")
      .blockOptional()
      .get()
  }

  fun createBudgetExpense(
    name: String = "Budget Expense",
    amountInCents: Int = 999,
    dueDate: LocalDate = LocalDate.now(),
    categoryId: Long,
    budgetId: Long,
  ): BudgetExpenseEntity {
    return budgetExpenseRepository
      .save(
        BudgetExpenseEntity(
          title = name,
          amountInCents = amountInCents,
          dueDate = dueDate,
          categoryId = categoryId,
          budgetId = budgetId,
        )
      )
      .log("create budget expense $name")
      .blockOptional()
      .get()
  }
}
