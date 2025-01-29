import io.ktor.plugin.features.*
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import io.ktor.plugin.features.JreVersion.JRE_11

val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val prometheusVersion: String by project
val koinVersion: String by project
val koinKtor: String by project
val mockkVersion: String by project
val appVersion: String by project
val detektVersion: String by project
val dockerImageName: String by project

group = "io.github.sanctumlabs"
version = appVersion

plugins {
    application
    kotlin("jvm") version "2.1.10"
    id("io.ktor.plugin") version "3.0.3"
    id("org.jetbrains.kotlin.plugin.serialization") version "2.1.0"
    id("io.gitlab.arturbosch.detekt") version "1.23.7"
}

repositories {
    mavenCentral()
}

configure<JavaPluginExtension> {
    sourceCompatibility = JavaVersion.VERSION_11
}

tasks {
    withType<JavaCompile> {
        sourceCompatibility = "${JavaVersion.VERSION_11}"
        targetCompatibility = "${JavaVersion.VERSION_11}"
    }

    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "${JavaVersion.VERSION_11}"
        }
    }
}

detekt {
    toolVersion = detektVersion
    source = files("src/main/kotlin", "src/test/kotlin")
}

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

ktor {
    docker {
        jreVersion.set(JRE_11)
        localImageName.set(dockerImageName)
        imageTag.set(appVersion)
        portMappings.set(
            listOf(
                DockerPortMapping(outsideDocker = 8080, insideDocker = 8080, protocol = DockerPortMappingProtocol.TCP)
            )
        )
        externalRegistry.set(
            DockerImageRegistry.dockerHub(
                appName = provider { dockerImageName },
                username = providers.environmentVariable("DOCKER_USERNAME"),
                password = providers.environmentVariable("DOCKER_PASSWORD")
            ),
        )
        // for Google Container Registry
//        externalRegistry.set(
//            DockerImageRegistry.googleContainerRegistry(
//                appName = provider { dockerImageName },
//                projectName = providers.environmentVariable("GOOGLE_PROJECT_NAME"),
//                username = providers.environmentVariable("GOOGLE_CONTAINER_REGISTRY_USERNAME"),
//                password = providers.environmentVariable("GOOGLE_CONTAINER_REGISTRY_PASSWORD")
//            )
//        )
    }
}

dependencies {
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-call-id-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-core-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-micrometer-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-metrics-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion")
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktorVersion")
    implementation("io.ktor:ktor-server-netty-jvm:$ktorVersion")

    implementation("io.insert-koin:koin-core:$koinVersion")
    implementation("io.insert-koin:koin-ktor:$koinKtor")
    implementation("io.insert-koin:koin-logger-slf4j:$koinKtor")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("io.micrometer:micrometer-registry-prometheus:$prometheusVersion")

    testImplementation("io.insert-koin:koin-test:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit4:$koinVersion")
    testImplementation("io.insert-koin:koin-test-junit5:$koinVersion")
    testImplementation("io.ktor:ktor-server-tests-jvm:$ktorVersion")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion")
    testImplementation("io.mockk:mockk:$mockkVersion")
}
