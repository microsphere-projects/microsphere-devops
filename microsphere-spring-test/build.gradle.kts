/*
 * Microsphere Spring Test module
 */
plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    // BOMs
    implementation(platform(libs.spring.boot.dependencies))
    implementation(platform(libs.microsphere.spring.dependencies))

    // Microsphere Spring
    implementation("io.github.microsphere-projects:microsphere-spring-context")

    // Jakarta EE
    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.transaction:jakarta.transaction-api")

    // Spring Framework
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-aop")
    implementation("org.springframework:spring-orm")

    // Spring Data JPA
    implementation("org.springframework.data:spring-data-jpa")

    // JPA Vendor - Hibernate
    implementation("org.hibernate.orm:hibernate-core")

    // Testing
    implementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.jetbrains.kotlin:kotlin-test-junit5")
    runtimeOnly("org.junit.platform:junit-platform-launcher")
    // H2 Database
    runtimeOnly("com.h2database:h2")
}