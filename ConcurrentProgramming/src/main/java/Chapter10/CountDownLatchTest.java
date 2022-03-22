package Chapter10;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CountDownLatchTest
 * @Description CountDownLatch线程同步器使用注意事项
 * 通常搭配线程池对线程进行统一的管理
 * 与join相比，可以使用countDown()方法让await方法返回，不一定非要等到线程完全结束，显得更加灵活
 * 但是这种计数器是一次性的，当计数器值到0之后，如果再次调用await或者countdown方法则会立刻返回
 * @Author lucius
 * @CreateTime 2022/3/22 16:50
 * @Version 1.0.0
 */
public class CountDownLatchTest {
    private static volatile CountDownLatch countDownLatch = new CountDownLatch(2);

    public static void main(String[] args) throws InterruptedException {
        // 同步计数器往往搭配线程池对线程进行优雅的管理，有效避免直接操作线程的join方法
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("child threadOne over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    System.out.println("child threadTwo over");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    countDownLatch.countDown();
                }
            }
        });

        System.out.println("wait all child thread over");

        // 等待子线程执行返回
        countDownLatch.await();

        System.out.println("all child thread over");

        executorService.shutdown();
    }
}
