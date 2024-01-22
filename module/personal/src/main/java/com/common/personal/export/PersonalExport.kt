package com.common.personal.export

import com.flyjingfish.module_communication_annotation.ExposeInterface


@ExposeInterface
interface PersonalExport {
    companion object {
        const val PERSONAL: String = "/personal"
        const val PUBLIC_PERSONAL_PERSONALDATAACTIVITY: String = "$PERSONAL/PersonalDataActivity"
        const val PUBLIC_PERSONAL_SETTINGACTIVITY: String = "$PERSONAL/SettingActivity"
    }
}