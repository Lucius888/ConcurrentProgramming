package Chapter10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @ClassName SemaphoreTest
 * @Description Semaphore信号量使用注意事项
 * 用于线程同步，但是其计数器是递增的
 * 不需要知道需要同步的线程个数，而是在需要同步的地方调用acquire方法进行同步
 * @Author lucius
 * @CreateTime 2022/3/22 18:59
 * @Version 1.0.0
 */
public class SemaphoreTest {
    private static Semaphore semaphore = new Semaphore(0);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread()+"over");
                    semaphore.release(); // 计数器加1
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println(Thread.currentThread()+"over");
                    semaphore.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        semaphore.acquire(2); // 意味着必须等到计数器为2时才会继续执行，否则一直阻塞
        System.out.println("all child thread over");

        executorService.shutdown();
    }
}
