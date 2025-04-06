plugins {
    alias(libs.plugins.kotlinJvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.shadow)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.mcp.kotlin)
    implementation(libs.slf4j)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.client.content.negotation)
    implementation(libs.ktor.serialization)
    implementation(libs.ktor.client.logging)
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

application {
    mainClass = "io.github.devcrocod.example.MainKt"
}

tasks.shadowJar {
    archiveFileName.set("serverAll.jar")
    archiveClassifier.set("")
    manifest {
        attributes["Main-Class"] = "io.github.devcrocod.example.MainKt"
    }
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}
