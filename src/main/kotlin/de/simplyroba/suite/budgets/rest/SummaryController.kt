package de.simplyroba.suite.budgets.rest

import de.simplyroba.suite.budgets.rest.model.MonthlySummary
import de.simplyroba.suite.budgets.service.SummaryService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("api/v1/summary")
@CrossOrigin
class SummaryController(
  private val summaryService: SummaryService,
) {

  @GetMapping("/year/{year}/month/{month}")
  fun getSummary(
    @PathVariable year: Int,
    @PathVariable month: Int,
  ): Mono<MonthlySummary> = summaryService.getSummaryForYearAndMonth(year, month)
}
