plugins {
    java
    `maven-publish`
    jacoco
    id("io.franzbecker.gradle-lombok") version "4.0.0"
    id("com.github.hierynomus.license") version "0.15.0"
    id("org.sonarqube") version "3.1.1"
}

group = "com.github.otymko.bsl"
version = "0.1.0"

repositories {
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

dependencies {
    // <!-- https://mvnrepository.com/artifact/commons-codec/commons-codec -->
    implementation("commons-codec", "commons-codec", "1.15")

    // <!-- https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp -->
    implementation("com.squareup.okhttp3", "okhttp", "4.9.1")

    // <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.dataformat/jackson-dataformat-xml -->
    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-xml", "2.12.1")

    // https://mvnrepository.com/artifact/com.thoughtworks.xstream/xstream
    implementation("com.thoughtworks.xstream", "xstream", "1.4.15")

    // lombok
    compileOnly("org.projectlombok", "lombok", lombok.version)
    annotationProcessor("org.projectlombok", "lombok", lombok.version)

    testImplementation(platform("org.junit:junit-bom:5.7.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj", "assertj-core", "3.19.0")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
    }
    reports {
        html.isEnabled = true
    }
}

tasks.check {
    dependsOn(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.isEnabled = true
        xml.destination = File("$buildDir/reports/jacoco/test/jacoco.xml")
    }
}

lombok {
    version = "1.18.18"
}

license {
    header = rootProject.file("licence/HEADER.txt")
    ext["year"] = "2021"
    ext["name"] = "Tymko Oleg <olegtymko@yandex.ru>"
    ext["project"] = "CRS-API for java"
    strictCheck = true
    mapping("java", "SLASHSTAR_STYLE")
    exclude("**/*.txt")
}

sonarqube {
    properties {
        property("sonar.projectKey", "otymko_crs-api-4j")
        property("sonar.projectName", "CRS API for Java")
        property("sonar.organization", "otymko")
        property("sonar.host.url", "https://sonarcloud.io")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.exclusions", "**/gen/**/*.*")
        property("sonar.coverage.jacoco.xmlReportPaths", "$buildDir/reports/jacoco/test/jacoco.xml")
    }
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }
}