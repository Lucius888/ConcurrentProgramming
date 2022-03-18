package Chapter1;

/**
 * @ClassName NotifyAndNotifyAllTest
 * @Description notify和notifyAll方法使用注意事项
 * 和wait方法一样，必须获取到共享变量的监视器锁之后才能够调用共享变量的notify方法
 * @Author lucius
 * @CreateTime 2022/3/16 21:52
 * @Version 1.0.0
 */
public class NotifyAndNotifyAllTest {
    //创建资源
    private static volatile Object resourceA = new Object();

    public static void main(String[] args) throws InterruptedException {
        //创建线程
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println("threadA get resourceA lock");
                    try {
                        System.out.println("threadA begin wait");
                        resourceA.wait();
                        System.out.println("threadA end wait");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //创建线程
        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resourceA) {
                    System.out.println("threadB get resourceA lock");
                    try {
                        System.out.println("threadB begin wait");
                        resourceA.wait();
                        System.out.println("threadB end wait");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        //创建线程
        Thread threadC = new Thread(new Runnable() {
            @Override
            public void run() {
                // 想要唤醒共享变量则必须先拿到他的锁
                synchronized (resourceA) {
                    System.out.println("threadC begin notify");
                    resourceA.notifyAll();
//                    resourceA.notify();
                }
            }
        });
        threadA.start();
        threadB.start();
        // 保证notify之前AB两个线程都在尝试获取锁
        Thread.sleep(1000);
        threadC.start();

        threadA.join();
        threadB.join();
        threadC.join();

        System.out.println("main over");
    }
}
