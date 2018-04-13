package com.example.rd.baomingxitong.RecyclerView

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.util.*

/**
 * Created by rd on 2017/9/24.
 */
abstract class MyAdapter<T>: RecyclerView.Adapter<ItemViewHolder>
{
    lateinit private var mDatas: List<T>
    private var mLayoutId: Int
    private var mVariableId: Int

    constructor(layoutid: Int,variableId: Int)
    {
        mLayoutId = layoutid
        mVariableId = variableId
    }

    fun setDatas(datas: List<T>)
    {
        mDatas = datas
    }

    override fun getItemCount(): Int {
        return mDatas.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        convert(holder,mDatas.get(position),position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
         var v: View = LayoutInflater.from(parent.context).inflate(mLayoutId,parent,false)

        return ItemViewHolder(v)
    }

    abstract fun convert(holder: ItemViewHolder,t: T,position: Int)
}
class ItemViewHolder: RecyclerView.ViewHolder
{
    private var mBingding: ViewDataBinding
    constructor(v: View):super(v)
    {
        mBingding = DataBindingUtil.bind(v)
    }

    fun getBinding(): ViewDataBinding
    {
        return mBingding
    }

    fun setBinding(variableId: Int,objects: Any): ItemViewHolder
    {
        mBingding.setVariable(variableId,objects)
        mBingding.executePendingBindings()
        return this
    }
}