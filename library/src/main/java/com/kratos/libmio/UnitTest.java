package com.kratos.libmio;

import java.lang.ref.WeakReference;

public class UnitTest {

    static Handler handler;

    public static void main(String [] args){

//        for (int i = 0;i < 2;i++){
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    Looper.prepar();
//
//                    System.out.printf("looper : %s,threadLocal : %s,threadId : %s\n",
//                            Looper.myLooper().hashCode(),
//                            Looper.myLooper().threadLocalCode(),
//                            Looper.myLooper().currentThreadId());
//                }
//            });
//            thread.start();
//        }

        Looper.prepare();
        Message msg = Message.obtain();
        handler = new Handler(){
            public void handleMessage(Message msg){
                long id = Thread.currentThread().getId();
                System.out.printf("message code : %s,thread id : %s\n",msg.hashCode(),id);
            }
        };
        handler.sendMessage(msg);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (Exception e) {}
                Message msg = Message.obtain();
                handler.sendMessage(msg);
                long id = Thread.currentThread().getId();
                System.out.printf("send thread:%s,send msg:%s\n",id,msg.hashCode());
            }
        }).start();

        Looper.loop();

        System.out.printf("this code never operating\n");

//        showMemoryCondition();
//        Message msg  = new Message();
//        msg.entry = "kratos";
//
////        Message strongReference = msg;
//
//        WeakReference<Message> weakReference = new WeakReference<>(msg);
//        showMemoryCondition();
//        msg = null;
//
//
//        Runtime.getRuntime().gc();
//
//        if (weakReference.get() != null) {
//            System.out.printf("garbage collect failed : %s\n",weakReference.get().entry);
//        } else {
//            System.out.printf("garbage collect succeed\n");
//        }
//
//
//
//        showMemoryCondition();
    }

    private static void showMemoryCondition(){
        Runtime runtime = Runtime.getRuntime();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
        System.out.printf("used memory : %s\n",(totalMemory - freeMemory));
    }
}