import net.minecraftforge.gradle.userdev.UserDevExtension
import org.gradle.jvm.tasks.Jar

// gradle.properties
val modGroup: String by extra
val modVersion: String by extra
val modBaseName: String by extra
val forgeVersion: String by extra
val mappingVersion: String by extra

buildscript {
    repositories {
        mavenCentral()
        maven(url = "https://maven.minecraftforge.net/")
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots")
    }
    dependencies {
        classpath("net.minecraftforge.gradle:ForgeGradle:4.1.+") {
            isChanging = true
        }
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.3.50")
    }
}

plugins {
    java
    scala
}

apply {
    plugin("net.minecraftforge.gradle")
    plugin("kotlin")
}

version = modVersion
group = modGroup

configure<UserDevExtension> {
    // the mappings can be changed at any time, and must be in the following format.
    // snapshot_YYYYMMDD   snapshot are built nightly.
    // stable_#            stables are built at the discretion of the MCP team.
    // Use non-default mappings at your own risk. they may not always work.
    // simply re-run your setup task after changing the mappings to update your workspace.
    mappings("stable",  "39-1.12")
    //mappings = mappingVersion
    // makeObfSourceJar = false // an Srg named sources jar is made by default. uncomment this to disable.

    accessTransformer(file("src/main/resources/META-INF/accesstransformer.cfg"))

    // Default run configurations.
    // These can be tweaked, removed, or duplicated as needed.
    runs {
        create("client") {
            workingDirectory(project.file("run"))

            // Recommended logging data for a userdev environment
            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")

            // Recommended logging level for the console
            property("forge.logging.console.level", "debug")
        }

        create("server") {
            workingDirectory(project.file("run"))

            // Recommended logging data for a userdev environment
            property("forge.logging.markers", "SCAN,REGISTRIES,REGISTRYDUMP")

            // Recommended logging level for the console
            property("forge.logging.console.level", "debug")
        }
    }
}

repositories {
    jcenter()
    mavenCentral()
//  Forgelin
    maven(url = "http://maven.shadowfacts.net/")
//  CurseForge
    maven(url = "https://www.cursemaven.com")
//  Fluidlogged API
    maven(url = "https://jitpack.io")
}

@Suppress("DEPRECATION")
dependencies {
    "minecraft"("net.minecraftforge:forge:1.12.2-14.23.5.2860")
    compile("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.3.50")
    compile("net.shadowfacts:Forgelin:1.8.4")
    compile("curse.maven:simpledifficulty-574162:3645250")
    compile("curse.maven:dynamictrees-252818:3613721")
//    compile("curse.maven:sereneseasons-291874:2799213")
//    compileOnly("curse.maven:bop-220318:2842510")

    // Fluidlogged API
    compileOnly("com.github.jbredwards:fluidlogged-api:ccf9bba5ec")

}


// processResources
val Project.minecraft: UserDevExtension
    get() = extensions.getByName<UserDevExtension>("minecraft")
tasks.withType<Jar> {
    // this will ensure that this task is redone when the versions change.
    inputs.property("version", project.version)

    @Suppress("DEPRECATION")
    baseName = modBaseName

    // replace stuff in mcmod.info, nothing else
    filesMatching("/mcmod.info") {
        expand(mapOf(
            "version" to project.version,
            "mcversion" to "1.12.2"
        ))
    }
}

// workaround for userdev bug
tasks.create("copyResourceToClasses", Copy::class) {
    tasks.classes.get().dependsOn(this)
    dependsOn(tasks.processResources.get())
    onlyIf { gradle.taskGraph.hasTask(tasks.getByName("prepareRuns")) }
    into("$buildDir/classes/kotlin/main")
    from(tasks.processResources.get().destinationDir)
}