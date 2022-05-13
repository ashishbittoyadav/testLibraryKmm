import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
//    id("signing")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    kotlin("plugin.serialization") version("1.6.21")
}


group = "in.sunfox.ecg-processor"
version = "1.0"

publishing {
    publications{
        create<MavenPublication>("ecg-processor"){
            groupId = group.toString()
            artifactId = "ecg-processor"
            version = version.toString()
//            from(components["java"])
        }
    }
    repositories {
        maven {
            name = "testLibrary"
            // change to point to your repo, e.g. http://my.org/repo
            url = uri("https://github.com/ashishbittoyadav/testLibraryKmm")
        }
    }
}

kotlin {
    android{
        publishLibraryVariants("release","debug")
        publishLibraryVariantsGroupedByFlavor = true
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "xcFrameWork"
            xcf.add(this)
        }
        version = "1.0.1"
    }

    multiplatformSwiftPackage{
        packageName("PeopleInSpace")
        swiftToolsVersion("5.3")
        targetPlatforms {
            iOS { v("13") }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies{
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")
                implementation("com.github.ashishbittoyadav:androidJitLib:1.0.0")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
        val androidMain by getting
        val androidTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosX64Test by getting
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosX64Test.dependsOn(this)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }
}

android {
    compileSdk = 32
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 32
    }
}
