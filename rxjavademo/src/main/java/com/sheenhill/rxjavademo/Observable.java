package com.sheenhill.rxjavademo;

/**
 * 具体的被观察者
 */
public abstract class  Observable implements ObservableSource {
    @Override
    public void subscribeObserver(Observer observer) {
        subscribeActual(observer);
    }

    protected abstract void subscribeActual(Observer observer);

    // 创建具体的观察者，给程序员用
    public static <T> Observable create(ObservableOnSubscribe<T> source){
        return new ObservableCreate<>(source);
    }
}
