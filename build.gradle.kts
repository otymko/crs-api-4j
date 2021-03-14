plugins {
    java
    id("io.franzbecker.gradle-lombok") version "4.0.0"
    id("com.github.hierynomus.license") version "0.15.0"
}

group = "com.github.otymko.bsl"
version = "0.1-SNAPSHOT"

repositories {
    mavenCentral()
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

tasks.test {
    useJUnitPlatform()
    testLogging {
        events("passed", "skipped", "failed")
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
