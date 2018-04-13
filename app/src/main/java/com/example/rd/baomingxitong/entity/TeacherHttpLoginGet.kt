package com.example.rd.baomingxitong.entity

/**
 * Created by rd on 2017/9/27.
 */
data class TeacherHttpLoginGet(
    var mima: String? = null     ,       //昵称
    var xingming: String? = null  ,      //老师姓名
    var gonghao: String? = null       ,     //学号
    var weixin: String? = null   ,         //微信号
    var shouji: String? = null   ,         //手机号
    var qq: String? = null      ,      //qq
    var jianjie: String? = null ,       //简介
    var xueli: String? = null ,       //学历
    var zhicheng: String? = null ,       //职称
    var teacherdbid: Int? = null ,           //数据库关联id
    var xueyuanId: Int ,           //学院
    var zhuanyeId: Int           //专业
)