plugins {
  id("org.springframework.boot") version "3.3.2"
  id("io.spring.dependency-management") version "1.1.6"
  id("com.diffplug.spotless") version "6.25.0"
  kotlin("jvm") version "1.9.24"
  kotlin("plugin.spring") version "1.9.24"
}

group = "de.simplyroba.suite"

version = file("version.txt").readText().trim()

java { toolchain { languageVersion = JavaLanguageVersion.of(21) } }

repositories { mavenCentral() }

dependencies {
  implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
  implementation("org.springframework.boot:spring-boot-starter-webflux")
  implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
  implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
  implementation("org.jetbrains.kotlin:kotlin-reflect")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
  implementation("org.liquibase:liquibase-core")
  implementation("org.springframework:spring-jdbc")
  testImplementation("org.springframework.boot:spring-boot-starter-test")
  testImplementation("org.springframework.boot:spring-boot-testcontainers")
  testImplementation("io.projectreactor:reactor-test")
  testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
  testImplementation("org.testcontainers:junit-jupiter")
  testImplementation("org.testcontainers:r2dbc")
  testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin { compilerOptions { freeCompilerArgs.addAll("-Xjsr305=strict") } }

tasks.withType<Test> {
  useJUnitPlatform()
  testLogging { showStandardStreams = true }
}

spotless {
  format("misc") {
    target("**/*.yml", "**/*.yaml", "**/.gitignore")
    targetExclude("build/**", ".idea/**", ".gradle/**")
    trimTrailingWhitespace()
    endWithNewline()
    indentWithSpaces(2)
  }

  kotlin {
    target("src/**/*.kt", "src/**/*.kts")
    targetExclude("build/**/*.kts", "build/**/*.kt")
    ktfmt().googleStyle()
    indentWithSpaces(2)
  }

  kotlinGradle {
    target("*.gradle.kts")
    targetExclude("build/**/*.gradle.kts")
    ktfmt().googleStyle()
  }

  freshmark { target("*.md") }
}
