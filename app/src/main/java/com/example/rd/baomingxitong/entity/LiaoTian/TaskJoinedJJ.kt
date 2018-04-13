package com.example.rd.baomingxitong.entity.LiaoTian

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

/**
 * Created by rd on 2017/11/5.
 */

data class TaskJoinedJJ(var xiangmu: ObservableField<String> = ObservableField(""),
                        var duizhang: ObservableField<String> = ObservableField(""),
                        var banji: ObservableField<String> = ObservableField(""),
                        var zhuangtai: ObservableField<String> = ObservableField("审核中"),
                        var qx: ObservableBoolean = ObservableBoolean(false))