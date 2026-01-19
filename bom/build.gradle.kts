plugins {
    `java-platform`
    id("bson-model3.publish-conventions")
}

description = "bsom-model/BOM"

dependencies {
    constraints {
        api(project(":core"))
    }
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["javaPlatform"])
            pom {
                name.set("bson-model3/BOM")
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
