package com.example.rd.baomingxitong.entity

import android.databinding.ObservableField
import android.databinding.ObservableInt

/**
 * Created by rd on 2017/9/24.
 */

data class UserInfo(var xuehao: ObservableField<String> = ObservableField(""),
                    var nicheng: ObservableField<String> = ObservableField(""),
                    var mima: ObservableField<String> = ObservableField(""),
                    var xingming: ObservableField<String> = ObservableField(""),
                    var shouji: ObservableField<String> = ObservableField(""),
                    var qq: ObservableField<String> = ObservableField(""),
                    var jianjie: ObservableField<String> = ObservableField(""),
                    var weixin: ObservableField<String> = ObservableField("")
)