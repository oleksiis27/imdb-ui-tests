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
    implementation("org.springframework.boot:spring-boot-starter")
    testImplementation("org.springframework:spring-test")
    testImplementation("org.springframework.boot:spring-boot-test")
    implementation("com.codeborne:selenide:$selenideVersion")
    implementation("org.testng:testng:$testngVersion")
    implementation("io.qameta.allure:allure-testng:$allureVersion")
    implementation("io.qameta.allure:allure-selenide:$allureVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    testCompileOnly("org.projectlombok:lombok:$lombokVersion")
    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")
    implementation("org.aeonbits.owner:owner:$ownerVersion")
    testImplementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("org.assertj:assertj-core:$assertjVersion")

}

tasks.test {
    useTestNG {
        suiteXmlFiles = listOf(file("testng.xml"))
    }
    systemProperty("headless", System.getProperty("headless", "false"))
    systemProperty("page.load.timeout", System.getProperty("page.load.timeout", "30"))
    systemProperty("timeout", System.getProperty("timeout", "10"))
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

tasks.named<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    enabled = false
}