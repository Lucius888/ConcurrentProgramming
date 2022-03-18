package Chapter1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName SleepTest
 * @Description sleep方法使用注意事项
 * sleep过程中不会释放当前持有的锁，如果sleep过程中被interrupt了,则会在sleep处抛出异常并返回
 * @Author lucius
 * @CreateTime 2022/3/17 10:42
 * @Version 1.0.0
 */
public class SleepTest {
    // 创建一个独占锁
    private static final Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        // 创建线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取独占锁
                lock.lock();
                try {
                    System.out.println("child threadA is in sleep");
                    Thread.sleep(10000);
                    System.out.println("child threadA is in awaked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // lock不会自动解锁，因此必须在finally中手动释放
                    lock.unlock();
                }
            }
        });

        // 创建线程
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                // 获取独占锁
                lock.lock();
                try {
                    System.out.println("child threadB is in sleep");
                    Thread.sleep(10000);
                    System.out.println("child threadB is in awaked");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // lock不会自动解锁，因此必须在finally中手动释放
                    lock.unlock();
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
