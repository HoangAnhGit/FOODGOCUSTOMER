plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.foodgocustomer"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.foodgocustomer"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures{
        viewBinding = true
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)


    // OkHttp logging
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


        // Retrofit core (phiên bản mới nhất)
        implementation("com.squareup.retrofit2:retrofit:2.11.0")

        // Converter cho JSON (Gson)
        implementation("com.squareup.retrofit2:converter-gson:2.11.0")

        // OkHttp (client cho Retrofit)
        implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.14")

        // Logging Interceptor để debug API
        implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.14")

        // Gson (nếu cần parse JSON thủ công)
        implementation("com.google.code.gson:gson:2.11.0")

        // AndroidX cơ bản
        implementation("androidx.appcompat:appcompat:1.7.0")
        implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    

}