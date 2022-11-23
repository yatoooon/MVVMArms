package com.common.res.config


import com.squareup.moshi.Json

data class UserInfoEntity(
    @Json(name = "permissions")
    val permissions: List<String> = listOf(),
    @Json(name = "roles")
    val roles: List<String> = listOf(),
    @Json(name = "user")
    val user: User = User()
) {
    data class User(
        @Json(name = "admin")
        val admin: Boolean = false,
        @Json(name = "avatar")
        val avatar: String = "",
        @Json(name = "clients")
        val clients: List<Client> = listOf(),
        @Json(name = "createBy")
        val createBy: String = "",
        @Json(name = "createTime")
        val createTime: String = "",
        @Json(name = "delFlag")
        val delFlag: String = "",
        @Json(name = "dept")
        val dept: Dept = Dept(),
        @Json(name = "deptId")
        val deptId: String = "",
        @Json(name = "email")
        val email: String = "",
        @Json(name = "loginDate")
        val loginDate: Long = 0,
        @Json(name = "loginIp")
        val loginIp: String = "",
        @Json(name = "nickName")
        val nickName: String = "",
        @Json(name = "params")
        val params: Params = Params(),
        @Json(name = "password")
        val password: String = "",
        @Json(name = "phonenumber")
        val phonenumber: String = "",
        @Json(name = "roles")
        val roles: List<Role> = listOf(),
        @Json(name = "sex")
        val sex: String = "",
        @Json(name = "status")
        val status: String = "",
        @Json(name = "userId")
        val userId: String = "",
        @Json(name = "userName")
        val userName: String = ""
    ) {
        data class Client(
            @Json(name = "clientName")
            val clientName: String = "",
            @Json(name = "id")
            val id: String = ""
        )

        data class Dept(
            @Json(name = "ancestors")
            val ancestors: String = "",
            @Json(name = "children")
            val children: List<Any> = listOf(),
            @Json(name = "createBy")
            val createBy: Any? = null,
            @Json(name = "createTime")
            val createTime: Any? = null,
            @Json(name = "delFlag")
            val delFlag: Any? = null,
            @Json(name = "deptId")
            val deptId: String = "",
            @Json(name = "deptName")
            val deptName: String = "",
            @Json(name = "email")
            val email: Any? = null,
            @Json(name = "leader")
            val leader: Any? = null,
            @Json(name = "orderNum")
            val orderNum: Int = 0,
            @Json(name = "params")
            val params: Params = Params(),
            @Json(name = "parentId")
            val parentId: String = "",
            @Json(name = "parentName")
            val parentName: Any? = null,
            @Json(name = "phone")
            val phone: Any? = null,
            @Json(name = "remark")
            val remark: Any? = null,
            @Json(name = "searchValue")
            val searchValue: Any? = null,
            @Json(name = "status")
            val status: String = "",
            @Json(name = "updateBy")
            val updateBy: Any? = null,
            @Json(name = "updateTime")
            val updateTime: Any? = null
        ) {
            class Params
        }

        class Params

        data class Role(
            @Json(name = "admin")
            val admin: Boolean = false,
            @Json(name = "createBy")
            val createBy: Any? = null,
            @Json(name = "createTime")
            val createTime: Any? = null,
            @Json(name = "dataScope")
            val dataScope: String = "",
            @Json(name = "delFlag")
            val delFlag: Any? = null,
            @Json(name = "deptCheckStrictly")
            val deptCheckStrictly: Boolean = false,
            @Json(name = "deptIds")
            val deptIds: Any? = null,
            @Json(name = "flag")
            val flag: Boolean = false,
            @Json(name = "menuCheckStrictly")
            val menuCheckStrictly: Boolean = false,
            @Json(name = "menuIds")
            val menuIds: Any? = null,
            @Json(name = "params")
            val params: Params? = null,
            @Json(name = "remark")
            val remark: Any? = null,
            @Json(name = "roleId")
            val roleId: String = "",
            @Json(name = "roleKey")
            val roleKey: String = "",
            @Json(name = "roleName")
            val roleName: String = "",
            @Json(name = "roleSort")
            val roleSort: String = "",
            @Json(name = "searchValue")
            val searchValue: Any? = null,
            @Json(name = "status")
            val status: String = "",
            @Json(name = "updateBy")
            val updateBy: Any? = null,
            @Json(name = "updateTime")
            val updateTime: Any? = null
        ) {
            class Params
        }
    }

}