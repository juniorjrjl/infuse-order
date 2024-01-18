plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "org.example"
version = "1.0"

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    mavenCentral()
}


val hibernateVersion = "5.6.3.Final"
val lombokVersion = "1.18.30"
val jacksonVersion = "2.16.1"
val mapstructVersion = "1.5.5.Final"
val junitVersion = "5.9.2"
val mockitoVersion = "4.11.0"

dependencies {
    implementation("com.google.inject:guice:7.0.0")
    implementation("com.sparkjava:spark-core:2.9.4")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-xml:$jacksonVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonVersion")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    implementation("org.liquibase:liquibase-core:4.25.1")
    implementation("com.mysql:mysql-connector-j:8.2.0")
    implementation("org.hibernate:hibernate-core:$hibernateVersion")
    implementation("org.glassfish:jakarta.el:3.0.3")
    implementation("org.hibernate.validator:hibernate-validator-cdi:6.2.5.Final")
    implementation("org.hibernate.validator:hibernate-validator:$hibernateVersion")


    compileOnly("org.hibernate:hibernate-jpamodelgen:$hibernateVersion")
    compileOnly("org.projectlombok:lombok:$lombokVersion")

    testCompileOnly("org.projectlombok:lombok:$lombokVersion")

    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
    annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.hibernate:hibernate-jpamodelgen:$hibernateVersion")

    testAnnotationProcessor("org.projectlombok:lombok:$lombokVersion")

    testImplementation(platform("org.junit:junit-bom:$junitVersion"))
    testImplementation("org.junit.jupiter:junit-jupiter:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junitVersion")
    implementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.assertj:assertj-core:3.25.1")
    testImplementation("net.datafaker:datafaker:1.9.0")



}

tasks.jar{
    manifest.attributes["Main-Class"] = "br.com.infuse.Main"
}

sourceSets{
    java{
        main{
            output.setResourcesDir(java.classesDirectory)
        }
    }
}

tasks.test {
    useJUnitPlatform()
}