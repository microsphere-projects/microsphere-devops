/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

plugins {
    // Support convention plugins written in Kotlin. Convention plugins are build scripts in 'src/main' that automatically become available as plugins in the main build.
    id("buildlogic.kotlin-library-conventions")
}


dependencies {

    // Internal Project
    api(project(":microsphere-devops-repository"))

    // Import the BOMs
    api(platform(libs.spring.cloud.alibaba.dependencies))
    api(platform(libs.microsphere.spring.cloud.dependencies))

    // Spring Cloud Commons
    api("org.springframework.cloud:spring-cloud-commons")

    // Nacos Discovery
    // api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
