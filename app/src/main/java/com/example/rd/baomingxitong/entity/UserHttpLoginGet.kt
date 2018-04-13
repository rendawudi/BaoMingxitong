package com.example.rd.baomingxitong.entity

/**
 * Created by rd on 2017/9/27.
 */
data class UserHttpLoginGet (
    var nicheng: String? = null,
    var mima: String? = null     ,       //昵称
    var xingming: String? = null  ,      //学生姓名
    var xuehao: String? = null       ,     //学号
    var weixin: String? = null   ,         //微信号
    var shouji: String? = null   ,         //手机号
    var qq: String? = null      ,      //qq
    var jianjie: String? = null ,       //简介

    var laoshi: Int? = null ,       //是否为老师
    var dbid: Int? = null ,           //数据库关联id
    var banjiId: Int,            //班级
    var xueyuanId: Int ,           //学院
    var zhuanyeId: Int           //专业
)