package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.ExpenseEntity
import de.simplyroba.suite.budgets.persistence.model.ExpenseTypePersistenceEnum
import java.time.LocalDate
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class ExpenseEntityToDtoConverterTest {

  private val converter = ExpenseEntityToDtoConverter(ExpenseTypePersistenceEnumToDtoConverter())

  @Test
  fun `should convert entity to dto`() {
    val entity =
      ExpenseEntity(
        id = 1,
        title = "title",
        amountInCents = 100,
        dueDate = LocalDate.now(),
        type = ExpenseTypePersistenceEnum.FIX,
        categoryId = 1,
        budgetId = 1,
      )

    val result = converter.convert(entity)

    assertThat(result.id).isEqualTo(entity.id)
    assertThat(result.title).isEqualTo(entity.title)
    assertThat(result.amountInCents).isEqualTo(entity.amountInCents)
    assertThat(result.dueDate).isEqualTo(entity.dueDate)
    assertThat(result.categoryId).isEqualTo(entity.categoryId)
    assertThat(result.budgetId).isEqualTo(entity.budgetId)
  }
}
