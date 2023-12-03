import org.gradle.internal.impldep.com.jcraft.jsch.ConfigRepository.defaultConfig

plugins {
    id("com.android.application")
}

android {
    namespace = "com.can301.gp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.can301.gp"
        minSdk = 29
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation ("com.jsibbold:zoomage:1.3.1")
    implementation ("com.github.bumptech.glide:glide:4.16.0")
    implementation ("com.github.MikeOrtiz:TouchImageView:1.4.1")
    implementation ("com.github.ybq:Android-SpinKit:1.4.0") // for fancy loading anifation
    implementation("com.github.lzyzsd:circleprogress:1.2.1") // for progress bar loading
    implementation("com.facebook.shimmer:shimmer:0.1.0@aar") // for shimmer laoding
    implementation("com.skyfishjy.ripplebackground:library:1.0.1") // for ripple effect
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.navigation:navigation-fragment:2.7.4")
    implementation("com.makeramen:roundedimageview:2.3.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")
    implementation("com.journeyapps:zxing-android-embedded:3.6.0"){setTransitive(false)}
    implementation("com.google.zxing:core:3.3.3")
    implementation("com.google.zxing:javase:3.0.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

}

