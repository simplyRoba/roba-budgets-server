package de.simplyroba.suite.budgets

import org.springframework.boot.fromApplication
import org.springframework.boot.with

fun main(args: Array<String>) {
	fromApplication<RobaBudgetsServerApplication>()
		.with(TestcontainersConfiguration::class)
		.run(*args)
}
