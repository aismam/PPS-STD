plugins {
    scala
    application
    `java-library`
    id("org.openjfx.javafxplugin") version "0.0.13"
    id("com.glovoapp.semantic-versioning") version "1.1.9"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}


version = project.version

tasks.jar {
    manifest {
        attributes(
                "Implementation-Title" to project.name,
                "Implementation-Version" to project.version,
                "Main-Class" to "View.GameLauncher"
        )
    }
    project.setProperty("archivesBaseName", "towerdefense")
}




repositories {
    mavenCentral()
}

dependencies {
    implementation("org.scala-lang:scala-library:2.13.8")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.scalafx:scalafx_2.13:18.0.2-R29")
    implementation("org.apache.logging.log4j:log4j-core:2.18.0")
    implementation("com.google.code.gson:gson:2.9.0")
}

testing {
    suites {
        val test by getting(JvmTestSuite::class) {
            useJUnit("4.13.2")

            dependencies {
                implementation("org.scalatest:scalatest_3:3.2.12")
                implementation("org.scalatestplus:junit-4-13_3:3.2.12.0")
                runtimeOnly("org.scala-lang.modules:scala-xml_2.13:2.1.0")
            }
        }
    }
}

javafx {
    version = "17.0.1"
    modules("javafx.base", "javafx.controls", "javafx.fxml", "javafx.graphics", "javafx.media", "javafx.swing", "javafx.web")
}


task("printVersion") {
    doLast {
        println("The project current version is ${project.semanticVersion.version.get()}")
    }
}

application {
    mainClass.set("View.GameLauncher")
}
