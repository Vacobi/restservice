plugins {
	java
	id("org.springframework.boot") version "3.4.3"
	id("io.spring.dependency-management") version "1.1.7"
}

group = "axiomatika.converter"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-web-services")
	annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

	/**
	 * Swagger
	 */
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.0.2")

	/**
	 * Database
	 */
	implementation("org.liquibase:liquibase-core")
	runtimeOnly("org.postgresql:postgresql")

	/**
	 * Test containers
	 */
	implementation(platform("org.testcontainers:testcontainers-bom:1.17.6"))
	testImplementation("org.testcontainers:junit-jupiter")
	testImplementation("org.testcontainers:postgresql")

	/**
	 * Apache HttpClient
	 */
	implementation("org.apache.httpcomponents:httpclient:4.5.13")

	/**
	 * org.json
	 */
	implementation("org.json:json:20231013")

	/**
	 * Tests
	 */
	testImplementation("org.junit.jupiter:junit-jupiter-params:5.9.2")
	testImplementation("org.assertj:assertj-core:3.24.2")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
