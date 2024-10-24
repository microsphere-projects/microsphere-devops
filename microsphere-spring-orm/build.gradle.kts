/*
 * Microsphere Spring ORM module
 */
plugins {
    id("buildlogic.kotlin-library-conventions")
}

dependencies {
    // BOMs
    implementation(platform(libs.spring.boot.dependencies))
    implementation(platform(libs.microsphere.spring.dependencies))

    // Microsphere Spring
    api("io.github.microsphere-projects:microsphere-spring-context")

    // Jakarta EE
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("jakarta.transaction:jakarta.transaction-api")

    // Spring Framework
    implementation("org.springframework:spring-context")
    implementation("org.springframework:spring-aop")
    implementation("org.springframework:spring-orm")

    // JPA Vendor - Hibernate
    implementation("org.hibernate.orm:hibernate-core")

    // Testing
    testImplementation(project(":microsphere-spring-test"))
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    // H2 Database
    testRuntimeOnly("com.h2database:h2")
}