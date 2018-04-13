package com.example.rd.baomingxitong.entity.Task

import android.databinding.ObservableField
import android.os.Parcel
import android.os.Parcelable
import java.util.*

/**
 * Created by rd on 2017/9/25.
 */
data class TaskmainGet (
    var xiangmu: String ="",
    var laoshi: String="",
    var jianjie: String="",
    var gonghao: String="",
    var xiangmuId: Int=0,
    var piciId: Int =0,
    var renshu: Int=0,
    var dqrenshu: Int=0,
    var startTime: Date,
    var endTime: Date
)
