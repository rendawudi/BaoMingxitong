package com.example.rd.baomingxitong.base

import android.app.Application
import android.database.sqlite.SQLiteDatabase
import com.example.rd.baomingxitong.GreenDao.DaoMaster
import com.example.rd.baomingxitong.GreenDao.DaoSession
import com.example.rd.baomingxitong.GreenDao.LTMsgDao
import com.example.rd.baomingxitong.GreenDao.LTXiangmuDao

/**
 * Created by rd on 2017/11/3.
 */

object GreenDaoUse
{
    lateinit var mHelper: DaoMaster.DevOpenHelper
    lateinit var db: SQLiteDatabase
    lateinit var mDaoMaster: DaoMaster
    lateinit var mDaoSession: DaoSession
    lateinit var mTaskLT: LTXiangmuDao
    lateinit var mMsgLt: LTMsgDao
    lateinit var app: MyApp

    fun init(app:MyApp)
    {
        this.app = app
        setDataBase()
    }

    fun setDataBase()
    {
        mHelper = DaoMaster.DevOpenHelper(app, "notes-db", null);
        db = mHelper.getWritableDatabase();
        // 注意：该数据库连接属于 DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
        mTaskLT = mDaoSession.ltXiangmuDao
        mMsgLt = mDaoSession.ltMsgDao
    }
}