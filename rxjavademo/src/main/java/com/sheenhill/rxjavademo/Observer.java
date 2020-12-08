package com.sheenhill.rxjavademo;

/**
 * 抽象的观察者
 * @param <T>
 */
public interface Observer<T> {
    // 接收消息
    void onNext(T t);

    // 建立关联，用这个api来通知
    void onSubscribe();

    // 异常
    void onError(Throwable e);

    // 接收消息完成
    void onComplete();
}
