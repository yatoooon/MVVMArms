plugins {
    `kotlin-dsl`
}
repositories {
    google()
    jcenter()
    maven {
        setUrl("https://maven.aliyun.com/nexus/content/groups/public/")
    }
}
dependencies {
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("com.android.tools.build:gradle:7.3.1")

    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")

    implementation("com.google.dagger:hilt-android-gradle-plugin:2.46")

    implementation("com.tencent.vasdolly:plugin:3.0.6")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())

}