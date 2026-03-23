pluginManagement {
    repositories {
        maven {
            url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
        }
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "bson-model3"
include(":bson-model3-bom")
include(":bson-model3-core")
include(":bson-model3-generator")