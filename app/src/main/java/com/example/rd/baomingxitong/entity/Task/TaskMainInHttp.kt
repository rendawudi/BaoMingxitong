package com.example.rd.baomingxitong.entity.Task

/**
 * Created by rd on 2017/10/3.
 */
data class TaskMainInHttp(
        var xiangmu: String ="",
        var laoshi: String="",
        var jianjie: String="",
        var xiangmuId: Int=0,
        var piciId: Int =0,
        var renshu: Int=0,
        var dqrenshu: Int=1,
        var xueyuanId: Int ?=0,
        var zhuanyeId: String ?= "",
        var xsxiangmu: Int = 0,
        var banjiId: String ?= "",
        var guoqi: Int =0
)