package com.kratos.libmio;

public class MessageQueue {

    private boolean mQuitting;

    Message mMessage;

    boolean enqueueMessage(Message msg){
        if (msg.target == null) {
            throw new IllegalArgumentException("Message must have a target.");
        }

        synchronized(this){
            if (mQuitting) {
                System.out.printf("sending message to a Handler on a dead thread");
                msg.recyle();
                return false;
            }

            Message p = mMessage;
            if (p == null) {
                msg.next = p;
                mMessage = msg;
            } else {
                Message prev;
                for (;;) {
                    prev = p;
                    p = p.next;
                    if (p == null) {
                        break;
                    }
                }
                msg.next = p;
                prev.next = msg;
            }
        }
        return true;
    }

    Message next() {
        for (;;) {
            synchronized (this){
                Message msg = mMessage;

                if (msg != null) {
                    mMessage = msg.next;
                    msg.next = null;
                    return msg;
                } else {
                    //no more message. it's blocking here
                }
            }
        }
    }

}