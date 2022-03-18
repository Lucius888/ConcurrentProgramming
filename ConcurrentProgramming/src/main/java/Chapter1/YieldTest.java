package Chapter1;

/**
 * @ClassName YieldTest
 * @Description yield方法使用注意事项
 * yield方法能够让出自己的时间片，但是不会像sleep那样阻塞程序，而是处于就绪状态，等待下一次调度
 * @Author lucius
 * @CreateTime 2022/3/17 11:04
 * @Version 1.0.0
 */
public class YieldTest implements Runnable {

    YieldTest() {
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            if ((i % 5) == 0) {
                System.out.println(Thread.currentThread()+"yield cpu");
                // 当前线程放弃cpu执行权，放弃时间片，进行下一轮的调度
                Thread.yield();
            }
        }
        // 由于执行权让出，此处的输出不会和上面的输出在一起
        System.out.println(Thread.currentThread()+"is over");

    }

    public static void main(String[] args) {
        new YieldTest();
        new YieldTest();
        new YieldTest();
    }
}
