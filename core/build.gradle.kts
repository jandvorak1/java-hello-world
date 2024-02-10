plugins {
  java 
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
}

java {
  sourceCompatibility = JavaVersion.VERSION_21
  targetCompatibility = JavaVersion.VERSION_21
}

tasks.jar {
  manifest {
    attributes["Built-By"] = System.getProperty("user.name")
 attributes["Specification-Title"] = project.name
        attributes["Specification-Version"] = "${project.version}"
        attributes["Specification-Vendor"] = "Jan Dvorak"
        attributes["Implementation-Title"] = project.name
        attributes["Implementation-Version"] = "${project.version}"
        attributes["Implementation-Vendor"] = "Jan Dvorak"
    attributes["Created-By"] = "Gradle ${project.gradle.gradleVersion}"
    attributes["Build-Jdk"] = "${System.getProperty("java.version")} (${System.getProperty("java.vendor").replace("N/A", "Arch Linux")} ${System.getProperty("java.vm.version")})"
    attributes["Build-OS"] = "${System.getProperty("os.name")} ${System.getProperty("os.arch")} ${System.getProperty("os.version")}"
  }
  exclude("**/*.xcf")
}

tasks.test {
  useJUnitPlatform {
    excludeTags("IntegrationTest")
  }
  jvmArgs("-Xshare:off")
  testLogging {
    events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
  }
}

tasks.register<Test>("integration"){
  useJUnitPlatform {
    includeTags("IntegrationTest")
  }
  jvmArgs("-Xshare:off")
  testLogging {
    events("PASSED", "SKIPPED", "FAILED", "STANDARD_OUT", "STANDARD_ERROR")
  }
}

tasks.compileJava {
  options.encoding = "UTF-8"
  options.isDeprecation = true
}

tasks.compileTestJava {
  options.encoding = "UTF-8"
  options.isDeprecation = true
}
