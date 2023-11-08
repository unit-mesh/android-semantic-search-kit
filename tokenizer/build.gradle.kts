import com.android.build.gradle.internal.tasks.factory.dependsOn

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.mozilla.rust-android-gradle.rust-android") version "0.9.3" apply true
}

android {
    namespace = "org.unitmesh.tokenizer"
    compileSdk = 34

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    gradle.projectsEvaluated {
        android.libraryVariants.all { variant ->
            println("${variant.name}........")
            variant.javaCompileProvider.dependsOn(tasks.getByName("processResources"))
            true
        }
    }
}

val version = "0.1.0"

tasks.register("processResources") {
    outputs.dir(file("${project.buildDir}/classes/main/native/lib"))
    doLast {
        val jnilibDir = "${project.projectDir}/jnilib/${version}"
        copy {
            from(jnilibDir)
            into("${project.buildDir}/classes/main/native/lib")
        }
    }
}

tasks.register("compileJNI") {
    doFirst {
        exec {
            commandLine("bash", "build.sh", version, "i686-linux-android")
        }
        exec {
            commandLine("bash", "build.sh", version, "armv7-linux-androideabi")
        }

        val ciDir = "${project.projectDir}/jnilib/0.1.0/"
        copy {
            from("${project.buildDir}/jnilib")
            into(ciDir)
        }
        File(System.getProperty("user.home") + "/.djl.ai/tokenizers").delete()
    }
}

cargo {
    module = "./rust"
    libname = "tokenizer"
    targets = listOf("arm", "x86")
    profile = "release"
}

dependencies {
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("ai.djl:api:0.25.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}