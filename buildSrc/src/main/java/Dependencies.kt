
/**
 * 版本信息
 */
object Versions {
    const val minSdk = 21
    const val targetSdk = 33
    const val compileSdk = 33
    const val buildTool = "33.0.0"
}

/**
 * 依赖库路径
 */
object Deps {
    //kotlin
    const val kotlinxCoroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.0"
    const val kotlinxCoroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.0"
    const val koinAndroid = "org.koin:koin-android:2.2.2"
    const val koinAndroidxScope = "org.koin:koin-androidx-scope:2.2.2"
    const val koinAndroidxViewmodel = "org.koin:koin-androidx-viewmodel:2.2.2"

    //anko
    const val anko = "org.jetbrains.anko:anko:0.10.8"
    const val ankoCommon = "org.jetbrains.anko:anko-common:0.10.8"
    const val kotlinStdlibJdk8 = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.7.22"

    //androidx
    const val appcompat = "androidx.appcompat:appcompat:1.6.1"
    const val design = "com.google.android.material:material:1.9.0"
    const val cardview = "androidx.cardview:cardview:1.0.0"
    const val annotationX = "androidx.annotation:annotation:1.6.0"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.3.0"
    const val room = "androidx.room:room-runtime:2.5.1"
    const val roomKtx = "androidx.room:room-ktx:2.5.1"
    const val roomCompiler = "androidx.room:room-compiler:2.5.1"
    const val lifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.6.1"
    const val lifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1"
    const val lifecycleRuntimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:2.6.1"
    const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
    const val lifecycleCompiler = "androidx.lifecycle:lifecycle-compiler:2.6.1"
    const val paging3 = "androidx.paging:paging-runtime:3.1.0-alpha03"


    const val lifecycleViewmodelSavedstate =
        "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.6.1"
    const val coreKtx = "androidx.core:core-ktx:1.10.0"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:1.5.7"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:2.1.4"

    //network
    const val retrofit = "com.squareup.retrofit2:retrofit:2.9.0"
    const val retrofitConverterMoshi = "com.squareup.retrofit2:converter-moshi:2.9.0"
    const val retrofitConverterGson = "com.squareup.retrofit2:converter-gson:2.9.0"
    const val retrofitAdapterRxjava = "com.squareup.retrofit2:adapter-rxjava:2.9.0"
    const val retrofitAdapterRxjava2 = "com.squareup.retrofit2:adapter-rxjava2:2.9.0"
    const val retrofit_helper = "com.github.jenly1314:retrofit-helper:1.1.0"

    const val okhttp = "com.squareup.okhttp3:okhttp:4.11.0"
    const val glide = "com.github.bumptech.glide:glide:4.15.1"
    const val glideCompiler = "com.github.bumptech.glide:compiler:4.15.1"
    const val okhttputils = "com.zhy:okhttputils:2.6.2"
    const val picasso = "com.squareup.picasso:picasso:2.8"

    //view
    const val roundedimageview = "com.makeramen:roundedimageview:2.3.0"
    const val autosize = "me.jessyan:autosize:1.2.1"
    const val baseRecyclerViewAdapterHelper =
        "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.11"
    const val titlebar = "com.wuhenzhizao:titlebar:1.2.0"
    const val smartShowAll = "com.github.the-pig-of-jungle.smart-show:all:3.0.3"
    const val smartRefreshLayoutKernel = "com.scwang.smart:refresh-layout-kernel:2.0.3"
    const val smartRefreshHeaderClassics = "com.scwang.smart:refresh-header-classics:2.0.3"
    const val lottie = "com.airbnb.android:lottie:6.0.0"
    const val shapeView = "com.github.getActivity:ShapeView:8.5"
    const val titleBar = "com.github.getActivity:TitleBar:10.5"
    const val Toaster = "com.github.getActivity:Toaster:12.2"
    const val XXPermissions = "com.github.getActivity:XXPermissions:18.3"
    const val loadsir = "com.kingja.loadsir:loadsir:1.3.8"
    const val photoview = "com.github.chrisbanes.photoview:library:1.2.3"
    const val tbssdk = "com.tencent.tbs.tbssdk:sdk:43993"
    const val xUpdate = "com.github.xuexiangjys:XUpdate:2.1.4"
    const val pictureSelector = "com.github.LuckSiege.PictureSelector:picture_library:v2.6.0"
    const val bottomNavigationViewEx = "com.github.ittianyu:BottomNavigationViewEx:2.0.4"
    const val vlayout = "com.alibaba.android:vlayout:1.3.0@aar"
    const val ycbannerlib = "cn.yc:YCBannerLib:1.4.0"
    const val banner = "io.github.youth5201314:banner:2.2.2"
    const val bgaQrcode = "cn.bingoogolapple:bga-qrcode-zxing:1.3.7"
    const val doodle = "com.github.1993hzw:Doodle:5.5.4"
    const val flowlayout = "com.hyman:flowlayout-lib:1.1.2"
    const val lfilepickerlibrary = "com.leon:lfilepickerlibrary:1.8.0"
    const val easyFloat = "com.github.princekin-f:EasyFloat:2.0.4"
    const val calendarview = "com.haibin:calendarview:3.7.1"
    const val clansFab = "com.github.clans:fab:1.6.4"
    const val andRatingBar = "com.github.giswangsj:AndRatingBar:1.0.5"
    const val androidPdfViewer = "com.github.barteksc:android-pdf-viewer:2.8.2"
    const val playerbase = "com.kk.taurus.playerbase:playerbase:3.4.2"
    const val dropDownMenu = "com.wdeo3601:drop-down-menu:1.0.5"
    const val labelsView = "com.github.donkingliang:LabelsView:1.6.3"
    const val circleProgressbar = "com.dinuscxj:circleprogressbar:1.3.6"
    const val rvAdapter = "com.gitee.cbfg5210:RVAdapter:0.3.5"
    const val circleindicator = "me.relex:circleindicator:2.1.6"
    const val bottombar = "me.majiajie:pager-bottom-tab-strip:2.4.0"
    const val spannable = "com.github.liangjingkanji:spannable:1.2.6"


    //tools
    const val dagger2 = "com.google.dagger:dagger:2.38.1"
    const val hiltAndroid = "com.google.dagger:hilt-android:2.31.1-alpha"
    const val hiltAndroidCompiler = "com.google.dagger:hilt-android-compiler:2.31.1-alpha"
    const val androidHiltLifecycleViewmodel = "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    const val androidHiltCompiler = "androidx.hilt:hilt-compiler:1.0.0"
    const val kotlinxMetadataJvm = "org.jetbrains.kotlinx:kotlinx-metadata-jvm:0.4.2"

    const val dagger2Compiler = "com.google.dagger:dagger-compiler:2.38.1"
    const val gson = "com.google.code.gson:gson:2.10.1"
    const val moshi_kotlin = "com.squareup.moshi:moshi-kotlin:1.15.0"
    const val moshi_kotlin_codegen = "com.squareup.moshi:moshi-kotlin-codegen:1.15.0"
    const val moshi_kotlin_nullsafe = "com.github.sparklexin:moshi-kotlin-nullsafe:1.0.0"
    const val multidex = "androidx.multidex:multidex:2.0.1"
    const val javaxAnnotation = "javax.annotation:jsr250-api:1.0"
    const val arouterApi = "com.alibaba:arouter-api:1.5.2"
    const val arouterCompiler = "com.alibaba:arouter-compiler:1.5.2"
    const val progressmanager = "me.jessyan:progressmanager:1.5.0"
    const val retrofitUrlManager = "me.jessyan:retrofit-url-manager:1.4.0"
    const val liveEventBusX = "com.jeremyliao:live-event-bus-x:1.7.3"
    const val jolyglotGson = "com.github.VictorAlbertos.Jolyglot:gson:0.0.6"
    const val timber = "com.jakewharton.timber:timber:5.0.1"
    const val zlwAudioRecorder = "com.github.zhaolewei:ZlwAudioRecorder:1.07"
    const val nineoldandroids = "com.nineoldandroids:library:2.4.0"
    const val butterknife = "com.jakewharton:butterknife:10.2.3"
    const val butterknifeCompiler = "com.jakewharton:butterknife-compiler:10.2.3"
    const val eventbus = "org.greenrobot:eventbus:3.2.0"
    const val mmkvStatic = "com.tencent:mmkv-static:1.2.16"
    const val greendao = "org.greenrobot:greendao:3.3.0"
    const val filedownloader = "com.liulishuo.filedownloader:library:1.7.7"
    const val smartkey = "com.github.Vove7.SmartKey:smartkey:6.3.8"
    const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0"
    const val viewbindingKtx = "com.dylanc:viewbinding-ktx:1.1.2"
    const val viewbindingBaseKtx = "com.dylanc:viewbinding-base-ktx:1.1.2"
    const val viewbindingBrvahKtx = "com.dylanc:viewbinding-brvah-ktx:1.1.2"
    const val logger = "com.orhanobut:logger:2.2.0"
    const val pinyin4j = "com.github.open-android:pinyin4j:2.5.0"
    const val vasdolly = "com.tencent.vasdolly:helper:3.0.6"
    const val codelocator = "com.bytedance.tools.codelocator:codelocator-core:2.0.3"



    //test
    const val junit = "junit:junit:4.13.2"
    const val extJunit = "androidx.test.ext:junit:1.1.5"
    const val espressoCore = "androidx.test.espresso:espresso-core:3.5.1"
    const val androidJUnitRunner = "androidx.test.runner.AndroidJUnitRunner"
    const val leakcanary = "com.squareup.leakcanary:leakcanary-android:2.10"
    const val aspectjrt = "org.aspectj:aspectjrt:1.9.19"

    const val umengCommon = "com.umeng.umsdk:common:9.5.8"
    const val umengAsms = "com.umeng.umsdk:asms:1.8.0"
    const val umengShareCore = "com.umeng.umsdk:share-core:7.3.2"
    const val umengShareWX = "com.umeng.umsdk:share-wx:7.3.2"
    const val umengShareQQ = "com.umeng.umsdk:share-qq:7.3.2"
    const val qqopensdk = "com.tencent.tauth:qqopensdk:3.53.0"
    const val wechatsdk = "com.tencent.mm.opensdk:wechat-sdk-android-without-mta:6.8.0"
    const val buglyCrashReport = "com.tencent.bugly:crashreport:4.1.9.2"
    const val buglyNativeCrashReport = "com.tencent.bugly:nativecrashreport:3.9.2"

}

