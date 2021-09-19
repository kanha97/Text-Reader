package com.devkanhaiya.bookreader.data.datasource

import com.devkanhaiya.bookreader.data.pojo.DataWrapper
import com.devkanhaiya.bookreader.data.pojo.ResponseBody
import com.devkanhaiya.bookreader.exception.ServerException
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

open class BaseDataSource {

    fun <T> execute(observable: Single<ResponseBody<T>>): Single<DataWrapper<T>> {
        return observable
                /*.subscribeOn(Schedulers.from(appExecutors.networkIO()))
                .observeOn(Schedulers.from(appExecutors.mainThread()))*/
                .subscribeOn(Schedulers.io())
                .map { t -> DataWrapper(t, null) }
                .onErrorReturn { t -> DataWrapper(null, t) }
                .map {
                    if (it.responseBody != null) {
                        when (it.responseBody.responseCode) {
                            0, 2, 3, 4, 11 -> return@map DataWrapper(it.responseBody, ServerException(it.responseBody.message, it.responseBody.responseCode))
                        }
                    }
                    return@map it
                }
    }

}