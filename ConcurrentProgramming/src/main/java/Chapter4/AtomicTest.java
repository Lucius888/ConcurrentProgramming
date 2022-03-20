package Chapter4;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName AtomicTest
 * @Description 原子类使用理解，以AtomicLong为例
 * @Author lucius
 * @CreateTime 2022/3/20 16:55
 * @Version 1.0.0
 */
public class AtomicTest {
    // 创建Long型原子计数器
    private static AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrayOne = new Integer[]{0, 1, 2, 3, 0, 56, 0, 56, 0};
    private static Integer[] arrayTwo = new Integer[]{10, 1, 2, 3, 0, 5, 6, 0, 56, 0};

    public static void main(String[] args) throws InterruptedException {
        // 统计数组中0的个数
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = arrayOne.length;
                for (int i = 0; i < size; ++i) {
                    if (arrayOne[i] == 0) {
                        atomicLong.incrementAndGet();
                    }
                }
            }
        });

        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                int size = arrayTwo.length;
                for (int i = 0; i < size; ++i) {
                    if (arrayTwo[i] == 0) {
                        atomicLong.incrementAndGet();
                    }
                }
            }
        });

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println("count 0:" + atomicLong.get());
    }
}
