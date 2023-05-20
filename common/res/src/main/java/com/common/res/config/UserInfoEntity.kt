package com.common.res.config


import com.squareup.moshi.Json

data class UserInfoEntity(
    @field:Json(name = "permissions")
    val permissions: List<String> = listOf(),
    @field:Json(name = "roles")
    val roles: List<String> = listOf(),
    @field:Json(name = "user")
    val user: User = User()
) {
    data class User(
        @field:Json(name = "admin")
        val admin: Boolean = false,
        @field:Json(name = "avatar")
        val avatar: String = "",
        @field:Json(name = "clients")
        val clients: List<Client> = listOf(),
        @field:Json(name = "createBy")
        val createBy: String = "",
        @field:Json(name = "createTime")
        val createTime: String = "",
        @field:Json(name = "delFlag")
        val delFlag: String = "",
        @field:Json(name = "dept")
        val dept: Dept = Dept(),
        @field:Json(name = "deptId")
        val deptId: String = "",
        @field:Json(name = "email")
        val email: String = "",
        @field:Json(name = "loginDate")
        val loginDate: Long = 0,
        @field:Json(name = "loginIp")
        val loginIp: String = "",
        @field:Json(name = "nickName")
        val nickName: String = "",
        @field:Json(name = "params")
        val params: Params = Params(),
        @field:Json(name = "password")
        val password: String = "",
        @field:Json(name = "phonenumber")
        val phonenumber: String = "",
        @field:Json(name = "roles")
        val roles: List<Role> = listOf(),
        @field:Json(name = "sex")
        val sex: String = "",
        @field:Json(name = "status")
        val status: String = "",
        @field:Json(name = "userId")
        val userId: String = "",
        @field:Json(name = "userName")
        val userName: String = ""
    ) {
        data class Client(
            @field:Json(name = "clientName")
            val clientName: String = "",
            @field:Json(name = "id")
            val id: String = ""
        )

        data class Dept(
            @field:Json(name = "ancestors")
            val ancestors: String = "",
            @field:Json(name = "children")
            val children: List<Any> = listOf(),
            @field:Json(name = "createBy")
            val createBy: Any? = null,
            @field:Json(name = "createTime")
            val createTime: Any? = null,
            @field:Json(name = "delFlag")
            val delFlag: Any? = null,
            @field:Json(name = "deptId")
            val deptId: String = "",
            @field:Json(name = "deptName")
            val deptName: String = "",
            @field:Json(name = "email")
            val email: Any? = null,
            @field:Json(name = "leader")
            val leader: Any? = null,
            @field:Json(name = "orderNum")
            val orderNum: Int = 0,
            @field:Json(name = "params")
            val params: Params = Params(),
            @field:Json(name = "parentId")
            val parentId: String = "",
            @field:Json(name = "parentName")
            val parentName: Any? = null,
            @field:Json(name = "phone")
            val phone: Any? = null,
            @field:Json(name = "remark")
            val remark: Any? = null,
            @field:Json(name = "searchValue")
            val searchValue: Any? = null,
            @field:Json(name = "status")
            val status: String = "",
            @field:Json(name = "updateBy")
            val updateBy: Any? = null,
            @field:Json(name = "updateTime")
            val updateTime: Any? = null
        ) {
            class Params
        }

        class Params

        data class Role(
            @field:Json(name = "admin")
            val admin: Boolean = false,
            @field:Json(name = "createBy")
            val createBy: Any? = null,
            @field:Json(name = "createTime")
            val createTime: Any? = null,
            @field:Json(name = "dataScope")
            val dataScope: String = "",
            @field:Json(name = "delFlag")
            val delFlag: Any? = null,
            @field:Json(name = "deptCheckStrictly")
            val deptCheckStrictly: Boolean = false,
            @field:Json(name = "deptIds")
            val deptIds: Any? = null,
            @field:Json(name = "flag")
            val flag: Boolean = false,
            @field:Json(name = "menuCheckStrictly")
            val menuCheckStrictly: Boolean = false,
            @field:Json(name = "menuIds")
            val menuIds: Any? = null,
            @field:Json(name = "params")
            val params: Params? = null,
            @field:Json(name = "remark")
            val remark: Any? = null,
            @field:Json(name = "roleId")
            val roleId: String = "",
            @field:Json(name = "roleKey")
            val roleKey: String = "",
            @field:Json(name = "roleName")
            val roleName: String = "",
            @field:Json(name = "roleSort")
            val roleSort: String = "",
            @field:Json(name = "searchValue")
            val searchValue: Any? = null,
            @field:Json(name = "status")
            val status: String = "",
            @field:Json(name = "updateBy")
            val updateBy: Any? = null,
            @field:Json(name = "updateTime")
            val updateTime: Any? = null
        ) {
            class Params
        }
    }

}