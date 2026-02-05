plugins {
    id("bson-model3.java-library-conventions")
    id("bson-model3.publish-conventions")
}

dependencies {

    api(project(":bson-model3-core"))
    compileOnly("org.jruby:jruby")

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine")
}

description = "bson-model3/Generator"

tasks.test {
    useJUnitPlatform()
    jvmArgs = jvmArgs + listOf("-server")
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
                name.set("bson-model3/Generator")
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
