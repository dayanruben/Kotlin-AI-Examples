plugins {
    kotlin("jvm") version "2.1.10"
    kotlin("plugin.spring") version "2.1.10"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    application
}

application {
    mainClass.set("MainKt")
}

group = "io.github.devcrocod.example"
version = "0.1.0"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.experimental:mcp:0.6.0")

}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}