/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */
plugins {
    id("buildlogic.kotlin-library-conventions")
    // Apply the all-open plugin to add support for Java Library
    // https://kotlinlang.org/docs/all-open-plugin.html
    kotlin("plugin.allopen") version "1.9.24"
    // Apply the no-arg plugin to add support for Java Library
    // https://kotlinlang.org/docs/no-arg-plugin.html
    kotlin("plugin.noarg") version "1.9.24"
}

dependencies {
    api(platform(libs.spring.boot.dependencies))
    api(platform(libs.spring.cloud.dependencies))

    implementation(libs.commons.lang3)
    implementation(libs.commons.io)

    // Jakarta EE
    compileOnly("jakarta.validation:jakarta.validation-api")
    compileOnly("jakarta.persistence:jakarta.persistence-api")
    compileOnly("jakarta.transaction:jakarta.transaction-api")

    // Spring Cloud Commons
    compileOnly("org.springframework.cloud:spring-cloud-commons")

    // JPA Vender - Hibernate
    compileOnly("org.hibernate.orm:hibernate-core")
}

noArg {
    annotation("jakarta.persistence.Entity")
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.Table")
    annotation("jakarta.persistence.Column")
    annotation("jakarta.persistence.Id")
    annotation("jakarta.persistence.GeneratedValue")
    annotation("jakarta.persistence.Embeddable")
    annotation("jakarta.persistence.MappedSuperclass")
}