package org.workfort.base.task

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.DefaultSubscriber


/*
*  ****************************************************************************
*  * Created by : Md. Azizul Islam on 11/6/2018 at 10:27 AM.
*  * Email : azizul@w3engineers.com
*  * 
*  * Purpose:
*  *
*  * Last edited by : Md. Azizul Islam on 11/6/2018.
*  * 
*  * Last Reviewed by : <Reviewer Name> on <mm/dd/yy>  
*  ****************************************************************************
*/

abstract class BaseTask<T> {
    fun execute(subscriber: DefaultSubscriber<T>) {
        val flowAble = createObservable()
        flowAble.subscribeOn(Schedulers.io()).subscribe { subscriber }
    }


    private fun createObservable(): Flowable<Any> {
        return Flowable.create<Any>({ e -> executeTask(e as FlowableEmitter<T>) }, BackpressureStrategy.BUFFER)
    }

    abstract fun executeTask(subscribe: FlowableEmitter<T>)
}