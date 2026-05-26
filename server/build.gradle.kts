plugins {
    kotlin("jvm") version "2.1.21"
    id("com.microsoft.azure.azurefunctions") version "1.16.0"
}

group = "com.example.suica"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation("com.microsoft.azure.functions:azure-functions-java-library:3.1.0")
    // Azure Identity for Managed Identity（Graph API 認証に使用）
    implementation("com.azure:azure-identity:1.13.3")
    // Microsoft Graph SDK
    implementation("com.microsoft.graph:microsoft-graph:6.23.0")
    // JSON シリアライズ
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.17.2")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.3")
    testImplementation("com.microsoft.azure.functions:azure-functions-java-library:3.1.0")
}

azurefunctions {
    resourceGroup = System.getenv("AZURE_RESOURCE_GROUP") ?: "suica-logger-rg"
    appName = System.getenv("AZURE_FUNCTION_APP_NAME") ?: "suica-logger-func"
    region = System.getenv("AZURE_REGION") ?: "japaneast"
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}
