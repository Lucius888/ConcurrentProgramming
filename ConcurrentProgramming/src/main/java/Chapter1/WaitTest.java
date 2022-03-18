package Chapter1;

/**
 * @ClassName WaitTest
 * @Description wait方法使用注意事项
 * 当线程调用共享对象的wait方法时， 当前线程只会释放当前共享对象的锁，当前线程持有的其他共享对象的监视器锁并不会被释放此时容易形成死锁
 * @Author lucius
 * @CreateTime 2022/3/16 21:52
 * @Version 1.0.0
 */
public class WaitTest {

    //创建资源
    private static volatile Object resourceA = new Object();
    private static volatile Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        //创建线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //获取资源的监视器锁，避免抛出非法监听异常
                    synchronized (resourceA) {
                        System.out.println("threadA get resourceA lock");

                        synchronized (resourceB) {
                            System.out.println("threadA get resourceB lock");
                            //挂起线程A，并释放锁
                            System.out.println("threadA release reourceA lock");
                            resourceA.wait();
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                    synchronized (resourceA) {
                        System.out.println("threadB get resourceA lock");

                        System.out.println("threadB try to get resourceB lock");
                        synchronized (resourceB) {
                            System.out.println("threadB get resourceB lock");

                            //挂起线程B，并释放锁
                            System.out.println("threadB release reourceA lock");
                            resourceA.wait();
                        }
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });

        threadA.start();
        threadB.start();

        //等待子线程结束，才能执行主线程内容
        threadA.join();
        threadB.join();

        System.out.println("main over");
    }
}
