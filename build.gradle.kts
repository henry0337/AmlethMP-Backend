import net.ltgt.gradle.errorprone.CheckSeverity
import net.ltgt.gradle.errorprone.errorprone

plugins {
	java
	idea
	alias(libs.plugins.spring.boot)
	alias(libs.plugins.dependency.management)
	id("org.sonarqube") version "7.2.2.6593"
	id("net.ltgt.errorprone") version "5.1.0"
}

group = "dev.sh1on"
version = "0.0.1-SNAPSHOT"
description = "Backend for AmlethMP application"

java {
	toolchain {
		languageVersion.set(JavaLanguageVersion.of(21))
	}
}

sonar {
	properties {
		property("sonar.projectKey", "AmlethMP")
		property("sonar.projectName", "AmlethMP")
	}
}

configurations {
	all {
		// Tự động cache module và phiên bản mới nhất (áp dụng cho thư viện được lấy từ mavenLocal())
		resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
		resolutionStrategy.cacheDynamicVersionsFor(0, TimeUnit.SECONDS)
	}

	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

idea {
	module {
		isDownloadJavadoc = false
		isDownloadSources = true
	}
}

extra["sentryVersion"] = "8.27.0"
extra["springCloudAzureVersion"] = "7.3.0"

dependencies {
	implementation(libs.spring.boot.starter.actuator)
	implementation(libs.spring.boot.starter.webflux)
	implementation(libs.spring.boot.starter.webclient)
	implementation(libs.liquibase.core)
	implementation(libs.spring.boot.starter.mail)
	implementation(libs.spring.boot.starter.validation)
	implementation(libs.spring.boot.starter.security)
	implementation(libs.spring.boot.starter.security.oauth2.resource.server)
	implementation(libs.spring.boot.starter.data.r2dbc)
	implementation(libs.spring.boot.starter.data.redis)
	implementation(libs.spring.jdbc)
	implementation(libs.kafka.streams)
	implementation(libs.spring.boot.starter.kafka)
	implementation(libs.spring.cloud.azure.starter)
	implementation(libs.spring.cloud.azure.starter.storage)
	implementation(libs.sentry.spring.boot.starter)
	implementation(libs.springdoc.openapi.webflux.ui)
	implementation(libs.hikari.cp)
	implementation(libs.mapstruct)
	implementation(libs.mapstruct.spring.annotations)
	implementation(libs.therapi.runtime.javadoc)
	implementation(libs.bundles.jjwt)
	implementation(libs.spring.dotenv)
	implementation(libs.resilience4j.spring.boot4)
	implementation(libs.myrlennia237.webflux)

	annotationProcessor(libs.mapstruct.processor)
	annotationProcessor(libs.therapi.runtime.javadoc.scribe)
	annotationProcessor(libs.lombok)
	annotationProcessor(libs.lombok.mapstruct.binding)
	annotationProcessor(libs.mapstruct.spring.extensions)
	annotationProcessor(libs.spring.boot.configuration.processor)

	runtimeOnly(libs.postgresql)
	runtimeOnly(libs.r2dbc.postgresql)

	compileOnly(libs.lombok)
	compileOnly(libs.jetbrains.annotations)

	developmentOnly(libs.spring.boot.devtools)
	developmentOnly(libs.spring.boot.docker.compose)

	errorprone("com.google.errorprone:error_prone_core:2.50.0")
	errorprone("com.uber.nullaway:nullaway:0.13.8")

	testImplementation(libs.spring.boot.starter.test)
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

	withType<JavaCompile> {
		options.compilerArgs.addAll(mapstructCompilerArgs())

		options.errorprone {
			disableAllChecks.set(true)
			check("NullAway", CheckSeverity.WARN)
			option("NullAway:AnnotatedPackages", "dev.sh1on.amlethmp")
			option("NullAway:CheckContracts", "true")
			excludedPaths.set(".*[/\\\\]build[/\\\\]generated[/\\\\].*")
		}

		if (name.lowercase().contains("test")) {
			options.errorprone {
				disable("NullAway")
			}
		}
	}

	bootBuildImage {
		runImage.set("paketobuildpacks/ubuntu-noble-run:latest")
	}
}

/**
 * Tạo danh sách tham số trình biên dịch cho MapStruct.
 */
private fun mapstructCompilerArgs(): List<String> {
	val args = mutableListOf(
		"-Amapstruct.defaultComponentModel=spring",
		"-Amapstruct.defaultInjectionStrategy=constructor",
		"-parameters"
	)

	if (project.hasProperty("dev")) {
		args.addAll(listOf(
			"-Amapstruct.suppressGeneratorTimestamp=true",
			"-Amapstruct.suppressGeneratorVersionInfoComment=true",
			"-Amapstruct.verbose=true"
		))
	}

	return args
}
