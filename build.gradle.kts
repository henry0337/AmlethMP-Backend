plugins {
	java
	idea
	eclipse
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.dependency.management)
}

group = "dev.sh1on"
version = "0.0.1-SNAPSHOT"
description = "Backend for Amleth's music player application"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(25)
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
		isDownloadJavadoc = true
		isDownloadSources = true
	}
}

extra["sentryVersion"] = "8.27.0"
extra["springCloudAzureVersion"] = "7.0.0"

dependencies {
	implementation(libs.spring.boot.starter.webflux)
	implementation(libs.spring.boot.starter.liquibase)
	implementation(libs.spring.boot.starter.mail)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.security.oauth2.resource.server)
	implementation(libs.spring.boot.starter.data.r2dbc)
	implementation(libs.spring.jdbc)
	implementation(libs.kafka.streams)
	implementation(libs.spring.boot.starter.kafka)
	implementation(libs.spring.cloud.azure.starter)
	implementation(libs.spring.cloud.azure.starter.storage)
	implementation(libs.sentry.spring.boot.starter)
	implementation(libs.springdoc.openapi.webflux.ui)
	implementation(libs.hikari.cp)
	implementation(libs.mapstruct)
	implementation(libs.therapi.runtime.javadoc)
	implementation(libs.bundles.jjwt)
	implementation(libs.bundles.poi)

	annotationProcessor(libs.mapstruct.processor)
	annotationProcessor(libs.therapi.runtime.javadoc.scribe)
	annotationProcessor(libs.lombok)
	annotationProcessor(libs.spring.boot.configuration.processor)

	runtimeOnly(libs.postgresql)
	runtimeOnly(libs.r2dbc.postgresql)

	compileOnly(libs.lombok)

	developmentOnly(libs.spring.boot.devtools)
//	developmentOnly(libs.spring.boot.docker.compose)

	testImplementation(libs.spring.boot.starter.test.webflux)
	testImplementation(libs.spring.boot.starter.test.security)
	testImplementation(libs.spring.boot.starter.test.validation)
	testImplementation(libs.spring.boot.starter.test.data.r2dbc)
	testImplementation(libs.spring.boot.starter.test.liquibase)
	testImplementation(libs.spring.boot.starter.test.mail)
	testImplementation(libs.spring.boot.starter.test.security.oauth2)
	testRuntimeOnly(libs.junit.platform.launcher)
	testAnnotationProcessor(libs.mapstruct.processor)
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

	bootBuildImage {
		runImage = "paketobuildpacks/ubuntu-noble-run:latest"
	}
}
