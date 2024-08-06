package de.simplyroba.suite.budgets

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RobaBudgetsServerApplication

fun main(args: Array<String>) {
    runApplication<RobaBudgetsServerApplication>(*args)
}
