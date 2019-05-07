package com.kratos.libh;


public class UnitTest {

    static Handler handler;

    public static void main(String [] args){
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

    }

}