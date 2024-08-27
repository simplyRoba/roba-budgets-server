package de.simplyroba.suite.budgets.config

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.MapPropertySource
import org.springframework.util.SystemPropertyUtils

/**
 * This listener will look for properties that start with the prefix system-properties. For each
 * matching property, it removes the prefix and sets the property as a system property, resolving
 * any placeholders in the value. This allows the application to use these properties as system
 * properties during runtime (System.getProperty("property")).
 */
class SystemPropertiesSetterEventListener :
  ApplicationListener<ApplicationEnvironmentPreparedEvent> {

  companion object {
    private const val SYSTEM_PROPERTIES_PREFIX = "system-properties."
  }

  override fun onApplicationEvent(event: ApplicationEnvironmentPreparedEvent) {
    event.environment.propertySources.forEach { ps ->
      if (ps is MapPropertySource) {
        ps.source
          .filter { it.key.toString().startsWith(SYSTEM_PROPERTIES_PREFIX) }
          .forEach {
            System.getProperties()
              .putIfAbsent(
                it.key.removePrefix(SYSTEM_PROPERTIES_PREFIX),
                SystemPropertyUtils.resolvePlaceholders(it.value.toString())
              )
          }
      }
    }
  }
}
