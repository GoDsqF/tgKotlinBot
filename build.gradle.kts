import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

application {
    mainClass.set("Main.kt")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://jitpack.io")
    mavenCentral()
}

val exposedVersion: String by project

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.elbekD:kt-telegram-bot:2.2.0")
    // Kotlin coroutine dependency
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")

    // MongoDB Kotlin driver dependency
    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

    implementation("org.slf4j:slf4j-api:2.0.13")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.23.1")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

application {
    mainClass.set("MainKt")
}