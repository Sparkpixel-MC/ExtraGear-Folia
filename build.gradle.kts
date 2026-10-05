plugins {
    java
    alias(libs.plugins.shadow)
}

group = "io.github.thebusybiscuit"
version = "1.2.0-Folia"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    // folia-api 26.2 ships Java 25 class files, so the plugin must target 25 as well.
    options.release = 25
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly(libs.folia.api)

    // Slimefun4 shades dough under io.github.thebusybiscuit.slimefun4.libraries.dough,
    // so its transitive dependencies are not needed at compile time.
    compileOnly(libs.slimefun4) {
        isTransitive = false
    }

    // bStats 3.x reports via AsyncScheduler and therefore works on Folia.
    implementation(libs.bstats.bukkit)

    // Bundled Folia-compatible GuizhanLib build.
    implementation(files("libs/GuizhanLib.jar"))
}

tasks.processResources {
    val props = mapOf("version" to project.version.toString())
    inputs.properties(props)
    filesMatching("plugin.yml") {
        expand(props)
    }
    from(rootProject.file("LICENSE"))
}

// Only the shaded JAR is shipped; the thin JAR would be an ambiguous
// candidate for the CI "Locate JAR" step.
tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveFileName = "ExtraGear v${project.version}.jar"
    minimize()
    relocate("org.bstats", "io.github.thebusybiscuit.extragear.bstats")
    relocate("net.guizhanss.guizhanlib", "io.github.thebusybiscuit.extragear.guizhanlib")
    relocate("net.guizhanss.minecraft.extragear.util", "io.github.thebusybiscuit.extragear.chineseutil")
    exclude("META-INF/maven/**")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}
