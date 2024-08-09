package de.simplyroba.suite.budgets

import de.simplyroba.suite.budgets.persistence.CategoryRepository
import de.simplyroba.suite.budgets.persistence.IncomeRepository
import de.simplyroba.suite.budgets.persistence.model.CategoryEntity
import de.simplyroba.suite.budgets.persistence.model.IncomeEntity
import io.r2dbc.spi.ConnectionFactory
import java.time.OffsetDateTime
import org.junit.jupiter.api.AfterEach
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.r2dbc.connection.init.ScriptUtils
import org.springframework.test.web.reactive.server.WebTestClient
import reactor.core.publisher.Mono

@Import(TestcontainersConfiguration::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class AbstractIntegrationTest {

  @Autowired lateinit var webTestClient: WebTestClient

  @Autowired private lateinit var connectionFactory: ConnectionFactory
  @Autowired private lateinit var incomeRepository: IncomeRepository
  @Autowired private lateinit var categoryRepository: CategoryRepository

  @AfterEach
  fun cleanUp(@Value("classpath:clean-up.sql") sqlScript: Resource) {
    // execute clean-up.sql script after each test as there is no @Sql annotation in R2DBC
    Mono.from(connectionFactory.create())
      .flatMap { connection -> ScriptUtils.executeSqlScript(connection, sqlScript) }
      .block()
  }

  fun createIncome(
    title: String = "Title",
    amountInCents: Int = 999,
    dueDate: OffsetDateTime = OffsetDateTime.now()
  ): IncomeEntity {
    return incomeRepository
      .save(IncomeEntity(title = title, amountInCents = amountInCents, dueDate = dueDate))
      .log("create income $title")
      .blockOptional()
      .get()
  }

  fun createCategory(name: String = "Category", parentCategoryId: Long? = null): CategoryEntity {
    return categoryRepository
      .save(CategoryEntity(name = name, parentCategoryId = parentCategoryId))
      .log("create category $name")
      .blockOptional()
      .get()
  }
}
