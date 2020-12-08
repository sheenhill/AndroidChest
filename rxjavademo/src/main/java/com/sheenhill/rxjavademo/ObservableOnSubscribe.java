package com.sheenhill.rxjavademo;

/**
 * 用来绑定发射器
 * @param <T>
 */
public interface ObservableOnSubscribe<T> {
    void subscribe(Emitter<T> emitter);
}
