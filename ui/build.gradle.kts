plugins {
  java 
  id("org.beryx.jlink") version "3.0.0" 
}

dependencies {
  testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.0")
  testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.0")
  implementation(project(":core"))
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

jlink {
  val tempDir: Provider<Directory> = layout.buildDirectory.dir("tmp")
  jlinkBasePath.set(tempDir.map { it.asFile.path })
  mergedModuleName.set("com.dvoraksw.jhw.merge")
  mergedModuleJarName.set("merge")
  addOptions("--no-header-files", "--no-man-pages", "--bind-services", "--ignore-signing-information")
  mainClass.set("com.dvoraksw.jhw.ui.Main")
  launcher {
    name = "jhw"
  }
  targetPlatform("linux-x64") {
    setJdkHome(System.getProperty("user.home") + "/.jdks/jvm/21/linux-x64")
  }
  targetPlatform("windows-x64") {
    setJdkHome(System.getProperty("user.home") + "/.jdks/jvm/21/windows-x64")
  }
  targetPlatform("macos-x64") {
    setJdkHome(System.getProperty("user.home") + "/.jdks/jvm/21/macos-x64/Contents/Home")
  }
}

application {
  mainModule.set("com.dvoraksw.jhw.ui")
  mainClass.set("com.dvoraksw.jhw.ui.Main")
}
