pluginManagement {
    // Include 'plugins build' to define convention plugins.
    includeBuild("build-logic")
}

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

rootProject.name = "microsphere-devops"

include(
    "microsphere-spring-orm",
    "microsphere-spring-data-jpa",
    "microsphere-devops-api",
    "microsphere-devops-repository",
    "microsphere-devops-service",
    "microsphere-devops-web",
)