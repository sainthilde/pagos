plugins {
	java
	//id("org.springframework.boot") version "4.0.6"
	//id("io.spring.dependency-management") version "1.1.7"
    id("jacoco")
	id("org.springframework.boot") version "3.5.9"
	id("io.spring.dependency-management") version "1.1.7"
	id("org.openapi.generator") version "7.3.0"
}

group = "pe.gob.vuce.cp2.bs"
version = "1.0.0"
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}
val openapiversion = "2.8.8"
val mapstructversion = "1.6.0"
val springcloudversion = "2025.0.0"
val openfeignVersion = "4.1.4"
dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.mapstruct:mapstruct:$mapstructversion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$openapiversion")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:$openfeignVersion")
    annotationProcessor("org.projectlombok:lombok")
    annotationProcessor ("org.mapstruct:mapstruct-processor:$mapstructversion")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    
}
dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:$springcloudversion")
    }
}

openApiGenerate {
    generatorName.set("spring")
    inputSpec.set("$projectDir/src/main/resources/openapi.yml")
    outputDir.set("$buildDir/generated/openapi")
    apiPackage.set("pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.api")
    modelPackage.set("pe.gob.vuce.cp2.bs.puertosnacionales.query.contract.model")
    configOptions.set(mapOf(
        "addRequestHeadersToAPI" to "true",
        "useSpringBoot3" to "true",
        "dateLibrary" to "java17",
        "generateApis" to "true",
        "generateModels" to "true",
        "interfaceOnly" to "true",
        "serializableModel" to "true",
        "useBeanValidation" to "true",
        "useTags" to "true",
        "implicitHeaders" to "true",
        "openApiNullable" to "false",
        "oas3" to "true"
    ))
}
tasks.withType<Test> {
    useJUnitPlatform()
    finalizedBy(tasks.jacocoTestReport)
}

tasks.jacocoTestReport {
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.named("processResources"){
    dependsOn(tasks.openApiGenerate)
}
tasks.named("compileJava"){
    dependsOn(tasks.openApiGenerate)
}
tasks.getByName<Jar>("jar") { enabled = false }

java.sourceSets["main"].java {
    srcDir("$buildDir/generated/openapi/src/main/java")
}
jacoco {
    toolVersion = "0.8.7"
}

val jacocoExclude = listOf("**/build/**", "**/generated/**")
tasks.withType<JacocoReport> {
    dependsOn(tasks.test)

    reports {
        xml.required.set(true)
        csv.required.set(true)
        html.required.set(true)
    }

    afterEvaluate {
        classDirectories.setFrom(files(classDirectories.files.map {
            fileTree(it).exclude(jacocoExclude)
        }))
    }
}
