package com.example.rd.baomingxitong.base

/**
 * Created by rd on 2017/9/27.
 */

object ActivityController
{
    var activityList: ArrayList<BaseActivity> = ArrayList<BaseActivity>()
    fun addActivity(activity: BaseActivity)
    {
        activityList.add(activity)
    }
    fun removeActivity(activity: BaseActivity)
    {
        activityList.remove(activity)
    }
    fun finishAll()
    {
        for (ac in activityList)
        {
            if (!ac.isFinishing)
                ac.finish()
        }
    }
}