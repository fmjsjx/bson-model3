plugins {
    id("bson-model3.java-library-conventions")
    id("bson-model3.publish-conventions")
}

dependencies {
    compileOnlyApi("org.jspecify:jspecify")

    implementation("org.slf4j:slf4j-api")

    api("com.github.fmjsjx:libcommon-collection")
    api("com.github.fmjsjx:libcommon-util")
    compileOnlyApi("com.github.fmjsjx:libcommon-json-jackson2")
    compileOnlyApi("com.github.fmjsjx:libcommon-json-jackson3")
    compileOnlyApi("com.github.fmjsjx:libcommon-json-fastjson2")
    compileOnlyApi("com.github.fmjsjx:libcommon-json-jsoniter")

    api("org.mongodb:bson")
    compileOnly("org.mongodb:mongodb-driver-core")
    compileOnly("org.mongodb:mongodb-driver-sync")
    compileOnly("org.mongodb:mongodb-driver-reactivestreams")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("org.apache.logging.log4j:log4j-slf4j2-impl")

}

description = "bson-model3/Core"

tasks.test {
    // Use junit platform for unit tests.
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            versionMapping {
                usage("java-api") {
                    fromResolutionOf("runtimeClasspath")
                }
                usage("java-runtime") {
                    fromResolutionResult()
                }
            }
            pom {
                name.set("bson-model3/Core")
                description.set("An ORM like object model framework for BSON/MongoDB.")
                url.set("https://github.com/fmjsjx/bson-model3")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                developers {
                    developer {
                        id.set("fmjsjx")
                        name.set("MJ Fang")
                        email.set("fmjsjx@163.com")
                        url.set("https://github.com/fmjsjx")
                        organization.set("fmjsjx")
                        organizationUrl.set("https://github.com/fmjsjx")
                    }
                }
                scm {
                    url.set("https://github.com/fmjsjx/bson-model3")
                    connection.set("scm:git:https://github.com/fmjsjx/bson-model3.git")
                    developerConnection.set("scm:git:https://github.com/fmjsjx/bson-model3.git")
                }
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}
