package Chapter1;

/**
 * @ClassName InterruptTest
 * @Description 线程中断 使用注意事项
 * boolean isInterrupted():检测当前线程（调用该方法的线程）是否被中断，返回中断标志位
 * boolean interrupted()：检测当前线程（语句所在的线程）是否被中断，返回中断标志位，同时还会清除中断标志位
 * @Author lucius
 * @CreateTime 2022/3/17 14:33
 * @Version 1.0.0
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (;;){}
            }
        });

        thread.start();
        thread.interrupt();
        System.out.println("isInterrupted:" + thread.isInterrupted()); // true
        System.out.println("isInterrupted:" + thread.interrupted()); // 判断的是主线程而不是thread,因此是false
        System.out.println("isInterrupted:" + Thread.interrupted()); // 同上 false
        System.out.println("isInterrupted:" + thread.isInterrupted()); // true

        thread.join();
        System.out.println("main thread is over");
    }
}
