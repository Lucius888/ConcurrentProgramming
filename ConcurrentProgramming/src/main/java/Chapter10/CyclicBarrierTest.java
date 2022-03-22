package Chapter10;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName CyclicBarrierTest
 * @Description CyclicBarrier回环屏障使用注意事项
 * 回环：当所有等待线程执行完毕，并重置CyclicBarrier状态后它可被重用
 * 屏障：线程调用await方法之后就会被阻塞，这个阻塞点就是屏障
 * 适合分段任务有序执行的工作场景
 * @Author lucius
 * @CreateTime 2022/3/22 17:11
 * @Version 1.0.0
 */
public class CyclicBarrierTest {
    //创建一个CyclicBarrier实例，添加一个所有子线程全部到达屏障后执行的任务
    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
        // 这个是当计数器为0，屏障被冲破后执行的任务
        @Override
        public void run() {
            System.out.println(Thread.currentThread() + "task merge result");
        }
    });

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "task-1");

                System.out.println(Thread.currentThread() + "enter in barrier");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread() + "task-2");

                System.out.println(Thread.currentThread() + "enter in barrier");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            }
        });

        // 关闭线程池
        executorService.shutdown();
    }
}
