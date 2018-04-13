package com.example.rd.baomingxitong.fuzhu

import android.content.Context
import android.os.RecoverySystem
import com.example.rd.jk1504mvvm.httpException.ExceptionEngine
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer

/**
 * Created by rd on 2017/11/3.
 */

class ProgressObservable<T>: Observer<T>,ProgressCancelListener
{
    var mcontext: Context
    var nextHandler: SubscribeOnNextListener<T>

    constructor(context: Context,nextHandler: SubscribeOnNextListener<T>)
    {
        mcontext = context
        this.nextHandler = nextHandler
    }

    override fun onComplete() {
        nextHandler.onComplete()
    }


    override fun onError(e: Throwable) {
        ExceptionEngine.handleException(e,mcontext)
    }

    override fun onSubscribe(d: Disposable) {
        nextHandler.onStart()
    }

    override fun onNext(t: T) {
        nextHandler.onNext(t)
    }

    override fun onProgressCanceled() {

    }
}