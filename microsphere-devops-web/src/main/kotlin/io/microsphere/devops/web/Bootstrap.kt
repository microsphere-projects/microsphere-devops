package io.microsphere.devops.web

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.runApplication

/**
 * Microsphere DevOps Service Bootstrap
 *
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy<a/>
 * @see EnableAutoConfiguration
 * @since 1.0.0
 */
@EnableAutoConfiguration
open class Bootstrap

fun main(args: Array<String>) {
    runApplication<Bootstrap>(*args)
}
