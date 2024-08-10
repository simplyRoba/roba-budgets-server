package de.simplyroba.suite.budgets.rest

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HealthController {

    @GetMapping("/health")
    fun health(): Mono<Unit> {
        return Mono.empty()
    }
}