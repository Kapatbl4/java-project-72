import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    id("checkstyle")
    id("io.freefair.lombok") version "8.3"
    id("com.github.ben-manes.versions") version "0.47.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    jacoco
}

application {
    mainClass.set("hexlet.code.App")
}


group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
        implementation("com.h2database:h2:2.2.224")
        implementation("org.postgresql:postgresql:42.7.1")
        implementation("com.zaxxer:HikariCP:5.1.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.16.1")
        implementation("org.apache.commons:commons-text:1.11.0")
        implementation("gg.jte:jte:3.1.9")
        implementation("org.slf4j:slf4j-simple:2.0.9")
        implementation("io.javalin:javalin:6.1.3")
        implementation("io.javalin:javalin-bundle:6.1.3")
        implementation("io.javalin:javalin-rendering:6.1.3")

        testImplementation("org.assertj:assertj-core:3.25.2")
        testImplementation(platform("org.junit:junit-bom:5.10.1"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation ("com.squareup.okhttp3:mockwebserver:4.12.0")
        implementation ("org.jsoup:jsoup:1.17.2")
        implementation ("com.konghq:unirest-java:3.13.0")

        annotationProcessor ("org.projectlombok:lombok:1.18.32")
        compileOnly("org.projectlombok:lombok:1.18.32")

        compileOnly("com.konghq:unirest-java-core:4.2.7")

}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
    reports {
        xml.required = true
        csv.required = false
        html.outputLocation = layout.buildDirectory.dir("jacocoHtml")
    }
}
