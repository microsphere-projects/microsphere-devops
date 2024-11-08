/*
 * Microsphere Spring JPA module
 */
plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    // BOMs
    // Spring Boot Dependencies
    implementation(platform(libs.spring.boot.dependencies))
    // Microsphere Spring Dependencies
    implementation(platform(libs.microsphere.spring.dependencies))

    // Internal API
    api(project(":microsphere-spring-orm"))

    // Jakarta EE
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.transaction:jakarta.transaction-api")

    // Spring Framework
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-aop")

    // Spring Data JPA
    implementation("org.springframework.data:spring-data-jpa")

    // JPA Vendor - Hibernate
    implementation("org.hibernate.orm:hibernate-core")

    // Testing
    testImplementation(project(":microsphere-spring-test"))
    testImplementation("org.hibernate.orm:hibernate-testing")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // H2 Database
    testRuntimeOnly("com.h2database:h2")
}