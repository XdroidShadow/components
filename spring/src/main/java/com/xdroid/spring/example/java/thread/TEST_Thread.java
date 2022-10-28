package com.xdroid.spring.example.java.thread;


import java.util.PriorityQueue;

/**
 * 多线程测试
 */
public class TEST_Thread {

    private int max = 20;
    private volatile int currentData = 0;
    private PriorityQueue<Integer> queueData = new PriorityQueue<>();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        TEST_Thread test = new TEST_Thread();

        Producer producer = test.new Producer();
        Customer customer = test.new Customer();
        producer.start();
        customer.start();

    }

    public void testThread() {


    }

    //生产者
    class Producer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (queueData) {
                    if (queueData.size() == max) {
                        try {
                            System.out.println("生产者 -数据已满，等待消费");
                            queueData.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queueData.notify();
                        }
                    }
                    System.out.println("生产者 - 生产：" + ++currentData);
                    queueData.offer(currentData);
                    queueData.notify();
                }
            }


        }
    }

    //消费者
    class Customer extends Thread {
        @Override
        public void run() {
            while (true) {
                synchronized (queueData) {
                    //为空
                    if (queueData.size() == 0) {
                        try {
                            System.out.println("消费者 - 无数据，等待生产");
                            queueData.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                            queueData.notify();
                        }
                    }
                    //不为空
                    Integer item = queueData.poll();
                    System.out.println("消费者 -- 消费：" + item);
                    queueData.notify();
                }
            }
        }
    }


}
