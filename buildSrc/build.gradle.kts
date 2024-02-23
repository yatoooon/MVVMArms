plugins {
    `kotlin-dsl`
}
repositories {
    google()
    jcenter()
    mavenCentral()
    maven {
        setUrl("https://maven.aliyun.com/nexus/content/groups/public/")
    }
}
dependencies {
    /* Depend on the android gradle plugin, since we want to access it in our plugin */
    implementation("com.android.tools.build:gradle:7.4.2")

    /* Depend on the kotlin plugin, since we want to access it in our plugin */
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.21")

    implementation("com.google.dagger:hilt-android-gradle-plugin:2.46")

    implementation("com.tencent.vasdolly:plugin:3.0.6")

    implementation("io.github.FlyJingFish.ModuleCommunication:module-communication-plugin:1.0.9")

    /* Depend on the default Gradle API's since we want to build a custom plugin */
    implementation(gradleApi())
    implementation(localGroovy())

}