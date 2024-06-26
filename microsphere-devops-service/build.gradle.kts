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

    api(platform(libs.spring.boot.dependencies))
    api(platform(libs.spring.cloud.dependencies))
    api(platform(libs.spring.cloud.alibaba.dependencies))
    api(platform(libs.microsphere.spring.cloud.dependencies))

    implementation(libs.commons.lang3)
    implementation(libs.log4j2.core)

    implementation(project(":microsphere-devops-repository"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("io.github.microsphere-projects:microsphere-spring-boot-actuator")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}
