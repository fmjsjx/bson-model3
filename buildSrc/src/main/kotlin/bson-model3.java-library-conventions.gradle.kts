plugins {
    `java-library`
}

repositories {
    maven {
        url = uri("https://mirrors.cloud.tencent.com/nexus/repository/maven-public/")
    }
    mavenCentral()
}

dependencies {
    // libcommon-bom
    api(platform("com.github.fmjsjx:libcommon-bom:4.1.1"))
    // mongodb-driver-bom
    api(platform("org.mongodb:mongodb-driver-bom:5.6.3"))
    // log4j2
    implementation(platform("org.apache.logging.log4j:log4j-bom:2.25.3"))
    // junit-bom
    testImplementation(platform("org.junit:junit-bom:6.0.2"))

    constraints {
        // JSpecify
        compileOnly("org.jspecify:jspecify:1.0.0")
        implementation("org.slf4j:slf4j-api:2.0.17")
        implementation("ch.qos.logback:logback-classic:1.5.24")
        api("com.jsoniter:jsoniter:0.9.23")
        val jrubyVersion = "10.0.2.0"
        implementation("org.jruby:jruby-complete:$jrubyVersion")
        implementation("org.jruby:jruby:$jrubyVersion")
        implementation("org.jruby:jruby-core:$jrubyVersion")
        implementation("org.jruby:jruby-stdlib:$jrubyVersion")
        implementation("org.yaml:snakeyaml:2.4")
    }

}

val javaVersion = 21

java {
    withSourcesJar()
    withJavadocJar()
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
    toolchain {
        languageVersion = JavaLanguageVersion.of(javaVersion)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.release = javaVersion
}

tasks.withType<Javadoc> {
    if (JavaVersion.current().isJava9Compatible) {
        (options as StandardJavadocDocletOptions).addBooleanOption("html5", true)
    }
    options.memberLevel = JavadocMemberLevel.PUBLIC
}
