buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        val kotlinVersion = "1.5.31"
        extra["kotlinVersion"] = kotlinVersion

        classpath("com.android.tools.build:gradle:4.2.2")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))

        classpath("android.arch.navigation:navigation-safe-args-gradle-plugin:1.0.0")
    }
}

plugins {
    id("io.gitlab.arturbosch.detekt") version "1.18.1"
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")
    ktlint {
        debug.set(false)
    }
}

detekt {
    toolVersion = "1.18.0"
    config = files("config/detekt-config.yml")
    allRules = true
    autoCorrect = true

    source = files("app/src/main/java", "app/src/main/kotlin")

    reports {
        html {
            enabled = true
            destination = file("app/build/detekt/detekt.html")
        }
    }
}
tasks.detekt {
    this.jvmTarget = "1.8"
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register<Delete>("clean") {
    delete(rootProject.buildDir)
}