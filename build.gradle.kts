plugins {
    id("org.jetbrains.intellij") version "0.4.16"
    kotlin("jvm") version "1.3.61"
}

group = "com.yemreak"
version = "1.0.2"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
}

// See https://github.com/JetBrains/gradle-intellij-plugin/
intellij {
    version = "2020.1"
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.getByName<org.jetbrains.intellij.tasks.PatchPluginXmlTask>("patchPluginXml") {
    val sinceBuild = "191" // Android compatibility

    val changelogPath = "$projectDir/.github/assets/CHANGELOG.html"
    val readmePath = "$projectDir/.github/assets/README.html"

    sinceBuild(sinceBuild)

    if (file(changelogPath).exists()) {
        changeNotes(file(changelogPath).readText(Charsets.UTF_8))
    }

    if (file(readmePath).exists()) {
        pluginDescription(file(readmePath).readText(Charsets.UTF_8))
    }
}

tasks.publishPlugin {
    token(file(".env").readText())
}