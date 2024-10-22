/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */
plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    // BOMs
    implementation(platform(libs.spring.boot.dependencies))
    implementation(platform(libs.microsphere.spring.dependencies))

    // Internal API
    api(project(":microsphere-spring-orm"))

    // Microsphere Spring
    api("io.github.microsphere-projects:microsphere-spring-context")
    
    // Jakarta EE
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.transaction:jakarta.transaction-api")

    // Spring Framework
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-aop")

    // Spring Data JPA
    implementation("org.springframework.data:spring-data-jpa")

    // JPA Vender - Hibernate
    implementation("org.hibernate.orm:hibernate-core")

    // Testing
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // H2 Database
    testRuntimeOnly("com.h2database:h2")
}