package de.simplyroba.suite.budgets

import java.time.Duration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitAllStrategy
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
class TestcontainersConfiguration {

  @Bean
  @ServiceConnection
  fun postgresContainer(): PostgreSQLContainer<*> {
    return PostgreSQLContainer(DockerImageName.parse("postgres:16-alpine"))
      // see
      // https://github.com/rancher-sandbox/rancher-desktop/issues/2609#issuecomment-1788871956
      .waitingFor(
        WaitAllStrategy()
          .withStrategy(Wait.forListeningPort())
          .withStrategy(
            Wait.forLogMessage(".*database system is ready to accept connections.*\\s", 2)
              .withStartupTimeout(Duration.ofSeconds(60))
          )
      )
  }
}
