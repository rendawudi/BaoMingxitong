package com.example.rd.baomingxitong.entity.LiaoTian

import android.databinding.ObservableBoolean
import android.databinding.ObservableField

/**
 * Created by rd on 2017/11/5.
 */

data class LTSxiaoxi(var time: ObservableField<String> = ObservableField(""),
                     var msg: ObservableField<String> = ObservableField(""),
                     var sender: ObservableField<String> = ObservableField(""),
                     var sfbr: Boolean = true
)