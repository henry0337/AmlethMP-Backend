plugins {
	java
	idea
	eclipse
	id("org.springframework.boot") version "4.0.2"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "dev.sh1on"
version = "0.0.1-SNAPSHOT"
description = "Backend for Amleth's music player application"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
}

idea {
	module {
		isDownloadJavadoc = false
		isDownloadSources = true
	}
}

eclipse {
	classpath {
		isDownloadJavadoc = false
		isDownloadSources = true
	}
}

extra["sentryVersion"] = "8.27.0"
extra["springCloudAzureVersion"] = "7.0.0"

dependencies {
	// Spring Boot WebFlux (Reactive)
	implementation("org.springframework.boot:spring-boot-starter-webflux")

	// Liquibase (Spring Boot)
	implementation("org.springframework.boot:spring-boot-starter-liquibase")

	// Mail Sender
	implementation("org.springframework.boot:spring-boot-starter-mail")

	// Validation
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// Spring Security
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server")

	// Spring Data R2DBC
	implementation("org.springframework:spring-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

	// Sentry
	implementation("io.sentry:sentry-spring-boot-4-starter")

	// Apache Kafka (Streams)
	implementation("org.apache.kafka:kafka-streams")
	implementation("org.springframework.boot:spring-boot-starter-kafka")

	// Microsoft Azure Storage
	implementation("com.azure.spring:spring-cloud-azure-starter")
	implementation("com.azure.spring:spring-cloud-azure-starter-storage")

	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	// Docker Compose
//	developmentOnly("org.springframework.boot:spring-boot-docker-compose")

	// Postgres
	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("org.postgresql:r2dbc-postgresql")

	// Lombok
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")

	// Manifold (Extension)
//	compileOnly("systems.manifold:manifold-ext:2025.1.31")
//	implementation("systems.manifold:manifold-ext-rt:2025.1.31")

	// Manifold (Properties)
//	compileOnly("systems.manifold:manifold-props:2025.1.31")
//	implementation("systems.manifold:manifold-props-rt:2025.1.31")

	// Manifold (Optional parameters & named arguments)
//	compileOnly("systems.manifold:manifold-params:2025.1.31")
//	implementation("systems.manifold:manifold-params-rt:2025.1.31")

	// Manifold (Strings)
//	implementation("systems.manifold:manifold-strings:2025.1.31")

	// Manifold (Collections)
//	implementation("systems.manifold:manifold-collections:2025.1.31")

	// Apache POI
	implementation("org.apache.poi:poi:5.5.0")
	implementation("org.apache.poi:poi-ooxml:5.5.0")

	// Swagger (WebFlux)
	implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.8.13")

	// HikariCP
	implementation("com.zaxxer:HikariCP:7.0.2")

	// MapStruct
	implementation("org.mapstruct:mapstruct:1.6.3")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")

	// Therapi Javadoc for OpenAPI
	implementation("com.github.therapi:therapi-runtime-javadoc:0.15.0")
	annotationProcessor("com.github.therapi:therapi-runtime-javadoc-scribe:0.15.0")

	// JJWT
	implementation("io.jsonwebtoken:jjwt-api:0.13.0")
	implementation("io.jsonwebtoken:jjwt-jackson:0.13.0")
	implementation("io.jsonwebtoken:jjwt-impl:0.13.0")

	testImplementation("org.springframework.boot:spring-boot-starter-data-r2dbc-test")
	testImplementation("org.springframework.boot:spring-boot-starter-liquibase-test")
	testImplementation("org.springframework.boot:spring-boot-starter-mail-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-oauth2-resource-server-test")
	testImplementation("org.springframework.boot:spring-boot-starter-security-test")
	testImplementation("org.springframework.boot:spring-boot-starter-validation-test")
	testImplementation("org.springframework.boot:spring-boot-starter-webflux-test")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.6.3")
}

dependencyManagement {
	imports {
		mavenBom("io.sentry:sentry-bom:${property("sentryVersion")}")
		mavenBom("com.azure.spring:spring-cloud-azure-dependencies:${property("springCloudAzureVersion")}")
	}
}

tasks {
	withType<Test> {
		useJUnitPlatform()
	}

//	withType<JavaCompile> {
//		options.compilerArgs = listOf("-Xplugin:Manifold")
//	}

	bootBuildImage {
		runImage = "paketobuildpacks/ubuntu-noble-run:latest"
	}
}
