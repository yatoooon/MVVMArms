package com.common.copy.app

import com.common.core.base.BaseApplication
import dagger.hilt.android.HiltAndroidApp

// copy之后 键入新的module名   然后修改 1.包名 2.自定义application 和 package 3. 清单文件里面相对应的
@HiltAndroidApp
class CopyApp : BaseApplication()