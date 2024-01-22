package com.common.test.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface TestExport {
    companion object {
        private const val GROUP: String = "/Test"

         const val publicTestTestActivity: String = "/Test/Activity/TestActivity"
        // const val publicTestTestFragment: String = "/Test/Fragment/TestFragment"

    }
}