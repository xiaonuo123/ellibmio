package com.kratos.libh;

import java.lang.reflect.Modifier;

public class Handler{

    final Looper mLooper;

    public void  handleMessage(Message msg){}

    final MessageQueue mQueue;

    public Handler(){
        this(false);
    }

    public Handler(boolean async) {
        if (true) {
            final Class<? extends Handler> klass = getClass();
            if ((klass.isAnonymousClass() || klass.isMemberClass() || klass.isLocalClass()) &&
                    (klass.getModifiers() & Modifier.STATIC) == 0) {
                System.out.printf("The following Handler class should be static or leaks might occur: "+
                        klass.getCanonicalName());
            }
        }

        mLooper = Looper.myLooper();
        if (mLooper == null) {
            throw new RuntimeException("Can't create handler inside thread that not called Looper.prepare()");
        }

        mQueue = mLooper.mQueue;
    }

    public final boolean sendMessage(Message msg) {
        MessageQueue queue = mQueue;
        if (queue == null) {
            throw new IllegalArgumentException("sendMessage() called with no mQueue");
        }
        msg.target = this;
        return queue.enqueueMessage(msg);
    }

    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }

}