
plugins {
    idea
    kotlin("jvm") version "1.3.21"
}

repositories {
    jcenter()
}


dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.0.1")
    implementation("io.javalin:javalin:2.6.0")
    implementation("org.slf4j:slf4j-api:1.7.25")

    testImplementation("io.kotlintest:kotlintest-runner-junit5:3.2.1")
}
