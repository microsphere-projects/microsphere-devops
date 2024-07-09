/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    id("buildlogic.kotlin-library-conventions")
    // Apply the spring plugin to add support for Spring specific features
    kotlin("plugin.spring") version "1.9.24"
    // Apply the JPA plugin to add support for JPA specific features
    kotlin("plugin.jpa") version "1.9.24"
}

dependencies {
    // Internal API
    api(project(":microsphere-devops-api"))

    // Kotlin
    api("org.jetbrains.kotlin:kotlin-reflect")

    // Spring Boot
    api("org.springframework.boot:spring-boot-starter-data-jpa")

    // Common Utils
    api(libs.commons.lang3)

    // JPA Vender - Hibernate
    api("org.hibernate.orm:hibernate-core")

    // Spring Cloud Commons
    implementation("org.springframework.cloud:spring-cloud-commons")

    // H2 Database
    runtimeOnly("com.h2database:h2")

    // Testing
    // Spring Boot Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // JPA Vender - Hibernate testing
    testImplementation("org.hibernate.orm:hibernate-testing")

}
