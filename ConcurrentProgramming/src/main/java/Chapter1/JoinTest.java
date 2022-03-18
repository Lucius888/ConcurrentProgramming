package Chapter1;

/**
 * @ClassName Chapter1.JoinTest
 * @Description join使用注意事项
 * A线程调用B线程的join方法后会被阻塞，此时如果有线程调用A线程的interrupt方法中断A线程时，A线程会抛出InterruptedException异常而返回。
 * @Author lucius
 * @CreateTime 2022/3/16 21:52
 * @Version 1.0.0
 */
public class JoinTest {
    public static void main(String[] args) throws InterruptedException {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("threadOne begin run");
                for(;;){}
            }
        });

        // 获取主线程
        final Thread mainThread = Thread.currentThread();
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //中断主线程
                mainThread.interrupt();
            }
        });

        threadOne.start();
        threadTwo.start();

        try {
            // 尝试等待线程结束
            threadOne.join();
        }catch (InterruptedException e){
            System.out.println("main thread" +e);
        }
    }
}
