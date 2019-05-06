package com.kratos.libmio;

public class Message {

    public String entry;

    public Handler target;

    public Message next;

    public int flags;

    static final int FLAG_IN_USE = 1 << 0;

    private static final int MAX_POOL_SIZE = 50;

    public static Message sPool;

    public static int sPoolSize = 0;

    public static final Object sPoolSync = new Object();

    public static Message obtain() {
        synchronized (sPoolSync) {
            if (sPool != null) {
                Message m = sPool;
                sPool = m.next;
                m.next = null;
                m.flags = 0;
                sPoolSize--;
                return m;
            }
        }
        return new Message();
    }

    public void recyle(){
        recycleUnchecked();
    }

    public void recycleUnchecked() {
        flags = FLAG_IN_USE;
        target = null;
        synchronized (sPoolSync) {
            if (sPoolSize < MAX_POOL_SIZE) {
                next = sPool;
                sPool = this;
                sPoolSize++;
            }
        }
    }
}