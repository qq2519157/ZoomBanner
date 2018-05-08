package com.pppcar.zoombanner.net;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by TANG on 2018-03-14.
 */

public final class RetrofitUtil {
    private static RetrofitUtil mInstance;

    private RetrofitUtil() {
    }

    public static RetrofitUtil getInstance() {
        if (mInstance == null) {
            synchronized (RetrofitUtil.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitUtil();
                }
            }
        }
        return mInstance;
    }


    public <T> Observable.Transformer<Response<T>, T> applySchedulers() {
//        return new Observable.Transformer<Response<T>, T>() {
//            @Override
//            public Observable<T> call(Observable<Response<T>> responseObservable) {
//                return responseObservable.subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .flatMap(new Func1<Response<T>, Observable<T>>() {
//                            @Override
//                            public Observable<T> call(Response<T> tResponse) {
//                                return flatResponse(tResponse);
//                            }
//                        })
//                        ;
//            }
//        };
        return (Observable.Transformer<Response<T>, T>) transformer;
    }



    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((Response<Object>) response);
                        }
                    });
        }
    };

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final Response<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {


                if (response.isSuccess()) {
                    if (!subscriber.isUnsubscribed()) {
                        T t = response.getData();
                        subscriber.onNext(t);
                        subscriber.onCompleted();
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new NetError(Integer.parseInt(response.getCode()), response.getMsg()));
                    }
                }

            }
        });
    }

}
