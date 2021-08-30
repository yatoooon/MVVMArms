plugins {
    `kotlin-dsl`
}
repositories {
    google()
    jcenter()
    mavenCentral()
}
dependencies {
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("com.android.tools.build:gradle:4.0.2")

    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.20")

    implementation("com.google.dagger:hilt-android-gradle-plugin:2.30.1-alpha")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())

}