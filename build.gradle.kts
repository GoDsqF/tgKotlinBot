import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.9.22"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    maven("https://jitpack.io")
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("com.github.elbekD:kt-telegram-bot:2.2.0")
}

dependencies {

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