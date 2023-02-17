plugins {
  java
  id("org.beryx.jlink") version "2.25.0"
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.0")
}

java {
  sourceCompatibility = JavaVersion.VERSION_17
  targetCompatibility = JavaVersion.VERSION_17
}

tasks.jar {
  manifest {
    attributes["Built-By"] = System.getProperty("user.name")
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
}

jlink {
  jlinkBasePath.set("$buildDir/tmp")
  mergedModuleName.set("com.dvoraksw.jhw.merge")
  mergedModuleJarName.set("jhw-merge")
  addOptions("--compress", "2", "--no-header-files", "--no-man-pages")
  mainClass.set("com.dvoraksw.jhw.core.Main")
  launcher {
    name = "jhw"
  }
  targetPlatform("linux-x64") {
    setJdkHome(System.getProperty("user.home") + "/.jdks/jvm/17/linux-x64")
  }
  targetPlatform("windows-x64") {
    setJdkHome(System.getProperty("user.home") + "/.jdks/jvm/17/windows-x64")
  }
  targetPlatform("macos-x64") {
    setJdkHome(System.getProperty("user.home") + "/.jdks/jvm/17/macos-x64/Contents/Home")
  }
}

application {
  mainModule.set("com.dvoraksw.jhw.core")
  mainClass.set("com.dvoraksw.jhw.core.Main")
}
