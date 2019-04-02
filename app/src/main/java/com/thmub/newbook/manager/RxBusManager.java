package com.thmub.newbook.manager;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by zhouas666 on 18-1-23.
 * PublishSubject本身作为观察者和被观察者。
 */

public class RxBusManager {
    private static volatile RxBusManager sInstance;
    private final PublishSubject<Object> mEventBus = PublishSubject.create();

    public static RxBusManager getInstance(){
        if (sInstance == null){
            synchronized (RxBusManager.class){
                if (sInstance == null){
                    sInstance = new RxBusManager();
                }
            }
        }
        return sInstance;
    }

    /**
     * 发送事件(post event)
     * @param event : event object(事件的内容)
     */
    public void post(Object event){
        mEventBus.onNext(event);
    }

    /**
     *
     * @param code
     * @param event
     */
    public void post(int code,Object event){
        Message msg = new Message(code,event);
        mEventBus.onNext(msg);
    }

    /**
     * 返回Event的管理者,进行对事件的接受
     * @return
     */
    public Observable toObservable(){
        return mEventBus;
    }

    /**
     *
     * @param cls :保证接受到制定的类型
     * @param <T>
     * @return
     */
    public <T> Observable<T> toObservable(Class<T> cls){
        //ofType起到过滤的作用,确定接受的类型
        return mEventBus.ofType(cls);
    }

    public <T> Observable<T> toObservable(int code,Class<T> cls){
        return mEventBus.ofType(Message.class)
                .filter(msg -> msg.code == code && cls.isInstance(msg.event))
                .map( msg -> (T)msg.event);
    }

    class Message{
        int code;
        Object event;

        public Message(int code,Object event){
            this.code = code;
            this.event = event;
        }
    }
}
