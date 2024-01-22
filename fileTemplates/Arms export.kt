package ${PACKAGE_NAME}.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface ${MODULE_NAME}Export {
    companion object {
         private const val GROUP: String = "/${MODULE_NAME}"

   // const val public${MODULE_NAME}TestActivity: String = "/${MODULE_NAME}/Activity/TestActivity"
   // const val public${MODULE_NAME}TestFragment: String = "/${MODULE_NAME}/Fragment/TestFragment"

    }
}