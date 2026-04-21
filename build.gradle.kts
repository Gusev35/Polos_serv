plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.serialization") version "1.9.22"
    application
}

group = "PolosServ"
version = "0.0.1"

application {
    mainClass.set("PolosServ.ApplicationKt")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=${project.ext.has("development")}")
}

repositories {
    mavenCentral()
}

dependencies {
    val ktorVersion = "2.3.7"

    // Ktor Core
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-cio-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")

    // Exposed ORM
    implementation("org.jetbrains.exposed:exposed-core:0.44.0")
    implementation("org.jetbrains.exposed:exposed-dao:0.44.0")
    implementation("org.jetbrains.exposed:exposed-jdbc:0.44.0")

    // Database
    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.zaxxer:HikariCP:5.1.0")

    // Logging
    implementation("ch.qos.logback:logback-classic:1.4.11")

    // Testing
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:1.9.22")
}

kotlin {
    jvmToolchain(17)  // Используем Java 17 (LTS)
}