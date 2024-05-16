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
        implementation("com.h2database:h2:2.2.222")
        implementation("com.zaxxer:HikariCP:5.0.1")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.15.2")
        implementation("org.apache.commons:commons-text:1.10.0")
        implementation("gg.jte:jte:3.1.0")
        implementation("org.slf4j:slf4j-simple:2.0.9")
        implementation("io.javalin:javalin:5.6.2")
        implementation("io.javalin:javalin-bundle:5.6.2")
        implementation("io.javalin:javalin-rendering:5.6.2")
        implementation("org.jsoup:jsoup:1.17.2")
        implementation("com.konghq:unirest-java:4.0.0-RC2")

        compileOnly("com.konghq:unirest-java-core:4.2.7")
        compileOnly("org.projectlombok:lombok:1.18.30")


        testImplementation("org.assertj:assertj-core:3.24.2")
        testImplementation(platform("org.junit:junit-bom:5.10.0"))
        testImplementation("org.junit.jupiter:junit-jupiter")
        testImplementation("com.squareup.okhttp3:mockwebserver:4.12.0")
}

tasks.test {
    useJUnitPlatform()
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

tasks.jacocoTestCoverageVerification {
    violationRules {
        rule {
            limit {
                minimum = "0.5".toBigDecimal()
            }
        }

        rule {
            isEnabled = false
            element = "CLASS"
            includes = listOf("org.gradle.*")

            limit {
                counter = "LINE"
                value = "TOTALCOUNT"
                maximum = "0.3".toBigDecimal()
            }
        }
    }
}
