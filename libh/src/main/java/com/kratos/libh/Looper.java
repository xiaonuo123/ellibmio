package com.kratos.libh;

public final class Looper{

    static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    final Thread mThread;
    final MessageQueue mQueue;

    private Looper(boolean quitAllowed){
        mQueue = new MessageQueue();
        mThread = Thread.currentThread();
    }

    public static void prepar(){
        prepar(true);
    }

    private static void prepar(boolean quitAllowed){
        if (sThreadLocal.get() != null) {
            throw new RuntimeException("Only one looper may be created per thread");
        }

        sThreadLocal.set(new Looper(quitAllowed));
    }

    public static void loop() {
        final Looper me = myLooper();

        if (me == null) {
            throw new RuntimeException("No Looper: Looper.prepare() wasn't called on this thread.");
        }

        final MessageQueue queue = me.mQueue;

        for (;;) {
            Message msg = queue.next();   //might block
            if (msg == null) {
                return;
            }

            msg.target.dispatchMessage(msg);

            msg.recycleUnchecked();
        }
    }

    public static void prepare() {
        prepare(true);
    }

    private static void prepare(boolean quitAllowed){
        sThreadLocal.set(new Looper(quitAllowed));
    }

    public static Looper myLooper(){
        return sThreadLocal.get();
    }

    public long currentThreadId(){
        return mThread.getId();
    }

    public int threadLocalCode() {
        return sThreadLocal.hashCode();
    }

}