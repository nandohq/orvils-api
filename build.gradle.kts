val koinVersion : String by project
val koinAnnotationsVersion : String by project

val logbackVersion : String by project
val valiktorVersion : String by project

val flywayVersion : String by project
val hikariVersion : String by project
val exposedVersion : String by project
val postgresVersion : String by project
val mapstructVersion : String by project

val javaVersion = JavaVersion.VERSION_17

group = "com.orvils"
version = "0.0.1"
java.sourceCompatibility = javaVersion
java.targetCompatibility = javaVersion

sourceSets.main {
    java.srcDirs("build/generated/ksp/main/kotlin")
}

application {
    mainClass.set("com.orvils.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

plugins {
    application
    kotlin("jvm") version "1.9.10"

    id("io.ktor.plugin") version "2.3.5"
    id("com.google.devtools.ksp") version "1.9.10-1.0.13"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.9.10"
}

repositories {
    mavenCentral()
}

dependencies {
    /* Ktor */
    implementation("io.ktor:ktor-server-core-jvm:2.3.6")
    implementation("io.ktor:ktor-server-netty-jvm:2.3.6")
    implementation("io.ktor:ktor-server-openapi:2.3.6")
    implementation("io.ktor:ktor-server-swagger-jvm:2.3.6")
    implementation("io.ktor:ktor-server-status-pages:2.3.6")
    implementation("io.ktor:ktor-server-request-validation:2.3.6")
    implementation("io.ktor:ktor-serialization-jackson:2.3.6")
    implementation("io.ktor:ktor-client-content-negotiation:2.3.6")
    implementation("io.ktor:ktor-server-content-negotiation:2.3.6")

    /* Koin DI */
    implementation("io.insert-koin:koin-core:${koinVersion}")
    implementation("io.insert-koin:koin-ktor:${koinVersion}")
    implementation("io.insert-koin:koin-annotations:${koinAnnotationsVersion}")
    ksp("io.insert-koin:koin-ksp-compiler:${koinAnnotationsVersion}")

    /* General */
    implementation("org.valiktor:valiktor-core:${valiktorVersion}")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.15.3")

    /* Database */
    implementation("com.zaxxer:HikariCP:${hikariVersion}")
    implementation("org.postgresql:postgresql:$postgresVersion")
    implementation("org.flywaydb:flyway-core:$flywayVersion")
    implementation("org.ktorm:ktorm-core:3.6.0")
    implementation("org.ktorm:ktorm-support-postgresql:3.6.0")

    /* Tests */
    testImplementation("io.ktor:ktor-server-tests-jvm:2.3.6")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.8.10")
}
