package de.simplyroba.suite.budgets.service.converter

import de.simplyroba.suite.budgets.persistence.model.FixExpenseTemplateEntity
import de.simplyroba.suite.budgets.persistence.model.RepeatIntervalPersistenceEnum
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class FixExpenseTemplateEntityToDtoConverterTest {

	private val converter =
		FixExpenseTemplateEntityToDtoConverter(RepeatIntervalPersistenceEnumToDtoConverter())

	@Test
	fun `should convert entity to dto`() {
		val entity =
			FixExpenseTemplateEntity(
				id = 1,
				title = "title",
				amountInCents = 100,
				repeatInterval = RepeatIntervalPersistenceEnum.MONTHLY,
				categoryId = 1,
			)

		val result = converter.convert(entity)

		assertThat(result.id).isEqualTo(entity.id)
		assertThat(result.title).isEqualTo(entity.title)
		assertThat(result.amountInCents).isEqualTo(entity.amountInCents)
		assertThat(result.categoryId).isEqualTo(entity.categoryId)
	}
}
