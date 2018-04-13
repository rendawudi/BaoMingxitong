package com.example.rd.baomingxitong.fuzhu

/**
 * Created by rd on 2017/11/3.
 */

interface SubscribeOnNextListener<T>
{
   fun onNext(t: T?)
   fun onComplete()
   fun onStart()
}