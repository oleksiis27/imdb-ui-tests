plugins {
    java
    id("io.qameta.allure") version "2.12.0"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.springframework.boot") version "3.4.3"
}

group = "com.imdb"
version = "1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

repositories {
    mavenCentral()
}

val selenideVersion = "7.7.3"
val testngVersion = "7.10.2"
val allureVersion = "2.29.1"
val lombokVersion = "1.18.36"
val ownerVersion = "1.0.12"
val assertjVersion = "3.27.3"

dependencies {
    // Spring Boot
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework:spring-test")
    implementation("org.springframework.boot:spring-boot-test")

    // Selenide
    implementation("com.codeborne:selenide:$selenideVersion")

    // TestNG
    implementation("org.testng:testng:$testngVersion")

    // Allure
    implementation("io.qameta.allure:allure-testng:$allureVersion")
    implementation("io.qameta.allure:allure-selenide:$allureVersion")

    // Lombok
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    // Owner (configuration)
    implementation("org.aeonbits.owner:owner:$ownerVersion")

    // Jackson (JSON test data)
    testImplementation("com.fasterxml.jackson.core:jackson-databind")

    // AssertJ
    implementation("org.assertj:assertj-core:$assertjVersion")

}

tasks.test {
    useTestNG {
        suiteXmlFiles = listOf(file("testng.xml"))
    }
    systemProperty("headless", System.getProperty("headless", "false"))
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

tasks.register<Test>("singleTest") {
    useTestNG {
        suiteXmlFiles = listOf(file("testng-single.xml"))
    }
    systemProperty("headless", System.getProperty("headless", "false"))
    testLogging {
        events("passed", "skipped", "failed")
        showStandardStreams = true
    }
}

allure {
    version.set(allureVersion)
    adapter {
        autoconfigure.set(true)
        aspectjWeaver.set(true)
    }
}

// Disable Spring Boot fat jar (this is a test project, not a deployable app)
tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}
