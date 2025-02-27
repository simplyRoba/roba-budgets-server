package de.simplyroba.suite.budgets.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/health")
class HealthController {

	@GetMapping("/check")
	fun health(): Mono<Unit> {
		return Mono.empty()
	}
}
