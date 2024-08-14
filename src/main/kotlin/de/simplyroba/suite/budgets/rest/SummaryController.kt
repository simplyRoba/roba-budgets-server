package de.simplyroba.suite.budgets.rest

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/summary")
@CrossOrigin
class SummaryController {

  fun getSummary() {
    TODO("do things")
  }
}
