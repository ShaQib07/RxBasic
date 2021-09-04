import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.4.32"
    application
}

group = "me.mlbd"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test-junit"))
    implementation("io.reactivex.rxjava3:rxjava:3.0.13")
    implementation("io.reactivex.rxjava3:rxkotlin:3.0.1")
}

tasks.test {
    useJUnit()
}

tasks.withType<KotlinCompile>() {
    kotlinOptions.jvmTarget = "1.8"
}

application {
    mainClass.set("MainKt")
}