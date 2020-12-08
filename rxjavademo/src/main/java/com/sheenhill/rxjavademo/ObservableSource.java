package com.sheenhill.rxjavademo;

/**
 * 顶层Observer
 * @param <T>
 */
public interface ObservableSource<T> {
    // 绑定Observable与Observer的联系
    void subscribeObserver(Observer observer);
}
