plugins {
    kotlin("jvm") version "2.1.10"
    `maven-publish`
}

group = "net.nouw"
version = "1.0.3"

repositories {
    mavenCentral()
}

sourceSets {
    main {
        kotlin {
            srcDir("generated/kotlin")
        }
    }
}

dependencies {
    implementation(kotlin("stdlib"))
    // Add your other dependencies here
    implementation("com.google.protobuf:protobuf-java:3.22.0")
    implementation("com.google.protobuf:protobuf-kotlin:3.22.0")
}

// Configure publishing to GitHub Packages
publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])  // Exposes the compiled JAR and accompanying metadata
            groupId = "net.nouw"
            artifactId = "convergence-of-crowns-model"
            version = "1.0.3"
        }
    }
    repositories {
        maven {
            name = "GitHubPackages"
            // Change the URL if needed. For a personal account, use your username.
            url = uri("https://maven.pkg.github.com/nouw/convergence-of-crowns-model")
            credentials {
                // Preferably, use project properties if set, or fall back to environment variables.
                username = project.findProperty("gpr.user") as String? ?: System.getenv("PACKAGE_ACTOR")
                password = project.findProperty("gpr.key") as String? ?: System.getenv("NPM_TOKEN")
            }
        }
    }
}
