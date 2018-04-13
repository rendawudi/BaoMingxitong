package com.example.rd.baomingxitong.entity.Task

import android.databinding.ObservableField
import android.databinding.ObservableInt

/**
 * Created by rd on 2017/10/2.
 */
data class TaskMsgXsInView(
        var renshuxianzhi: ObservableInt = ObservableInt(0),
        var yiyourenshu: ObservableInt = ObservableInt(0),
        var xiangmu: ObservableField<String> = ObservableField(""),
        var shenbaoren: ObservableField<String> = ObservableField(""),
        var laoshi: ObservableField<String> = ObservableField(""),
        var banji: ObservableField<String> = ObservableField(""),
        var renshu: ObservableField<String> = ObservableField(""),
        var xuehao: ObservableField<String> = ObservableField(""),
        var jingfei: ObservableField<String> = ObservableField(""),
        var jianjie: ObservableField<String> = ObservableField(""),
        var fangan: ObservableField<String> = ObservableField(""),
        var jindu: ObservableField<String> = ObservableField(""),
        var tese: ObservableField<String> = ObservableField(""),
        var chengguo: ObservableField<String> = ObservableField(""),
        var xuehao1: ObservableField<String> = ObservableField(""),
        var fengong1: ObservableField<String> = ObservableField(""),
        var xuehao2: ObservableField<String> = ObservableField(""),
        var fengong2: ObservableField<String> = ObservableField(""),
        var xuehao3: ObservableField<String> = ObservableField(""),
        var fengong3: ObservableField<String> = ObservableField(""),
        var xuehao4: ObservableField<String> = ObservableField(""),
        var fengong4: ObservableField<String> = ObservableField(""),
        var xuehao5: ObservableField<String> = ObservableField(""),
        var fengong5: ObservableField<String> = ObservableField("")
)