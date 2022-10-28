package com.xdroid.spring.example.java.thread;

import java.util.concurrent.*;

/**
 * 使用阻塞队列进行多线程
 */
public class TEST_Blocking {
    private int current = 0;
    private ArrayBlockingQueue<Integer> data = new ArrayBlockingQueue<Integer>(20);

    public static void main(String[] args) {
//        XDThreadTest2 test = new XDThreadTest2();
//        Customer customer = test.new Customer();
//        Producer producer = test.new Producer();
//        customer.start();
//        producer.start();

        XDRunnable xdRunnable = new XDRunnable();
        new Thread(xdRunnable, "线程1").start();
        new Thread(xdRunnable, "线程2").start();
        new Thread(xdRunnable, "线程3").start();

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 2, 10L,
                TimeUnit.MICROSECONDS, new LinkedBlockingDeque<>());



    }


    class Customer extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Integer take = data.take();
                    //这样的打印不是同步的，会出现数据错位
                    System.out.println("消费者 -- 消费：" + take);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Producer extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    data.put(++current);
                    //这样的打印不是同步的，会出现数据错位
                    System.out.println("生产者 - 生产：" + current);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class XDRunnable implements Runnable {
        private  int count = 100;

        @Override
        public void run() {
            while (true) {
                synchronized (this) {
                    if (count <= 0) {
                        System.out.println(Thread.currentThread().getName() + "  count = " + count);
                        break;
                    }
                    System.out.println(Thread.currentThread().getName() + "  取走 count = " + count--);
                }
            }
        }
    }

}
