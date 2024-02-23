-keep class !com.common.**,** {*;}
-keep class com.common.res.aop.** {*;}
-keep class com.common.res.http.net.** {*;}
-keep class com.common.**.mvvm.model.** {*;}
-keep class * extends androidx.databinding.ViewDataBinding{*;}
-keep class * extends com.common.core.base.mvvm.BaseViewModel{*;}
-keep class * extends com.common.core.config.CoreConfigModule{*;}
-keep class * extends com.chad.library.adapter.base.viewholder.BaseViewHolder { *; }
-keep class * extends com.common.res.adapter.DataBindingViewHolder { *; }
-keep class * implements com.alibaba.android.arouter.facade.template.ISyringe{*;}
-keep interface * implements com.alibaba.android.arouter.facade.template.IProvider


