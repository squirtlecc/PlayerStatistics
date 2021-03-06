

group = "cc.squirtle"
version = "0.2"

plugins {
    // basic dependencies java and kotlin jvm
    id("java")
    kotlin("jvm") version "1.4.31"


}

repositories {
    // spigot api mirror now using kr.entree.spigradle dont need it
    maven{url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")}

    // maven mirrors in china
    maven{url = uri("https://maven.aliyun.com/repository/google")}
    maven{url = uri("https://maven.aliyun.com/repository/public")}
    maven{url = uri("https://maven.aliyun.com/repository/jcenter")}
}


dependencies {

    // basic dependencies
    implementation(kotlin("stdlib"))
    //compileOnly(spigot("1.16.5"))
    compileOnly("org.spigotmc:spigot-api:1.16.5-R0.1-SNAPSHOT")
    // test jar
    testCompile("junit", "junit", "4.12")

}



// fat jar :include all dependencies
//gradlew build
tasks.withType<Jar> {

    from(sourceSets.main.get().output)
    isZip64 = true
    dependsOn(configurations.runtimeClasspath)

    from({
        configurations.runtimeClasspath.get().filter {
            it.name.endsWith("jar")
        }.map { zipTree(it) }

    })
}

