package de.simplyroba.suite.budgets.integration

import de.simplyroba.suite.budgets.AbstractIntegrationTest
import org.junit.jupiter.api.Test

class HealthIntegrationTest : AbstractIntegrationTest() {
	@Test
	fun `health endpoint should return 200`() {
		webTestClient.get().uri("/health/check").exchange().expectStatus().isOk
	}
}
