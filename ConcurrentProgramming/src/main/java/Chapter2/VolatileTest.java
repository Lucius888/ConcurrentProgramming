package Chapter2;

/**
 * @ClassName VolatileTest
 * @Description 指令重排问题讨论
 * 写线程中的两个赋值命令存在指令重排的可能性，因此num可能是0可能是2
 * @Author lucius
 * @CreateTime 2022/3/17 19:15
 * @Version 1.0.0
 */
public class VolatileTest {
    private static int num = 0;
    private static boolean ready = false;

    static class ReadThread extends Thread {
        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                if(ready){
                    System.out.println(num+num);
                }
                System.out.println("read thread...");
            }
        }
    }

    static class Writethread extends Thread{
        public void run(){
            num=2;
            ready = true;
            System.out.println("write thread set over");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReadThread readThread = new ReadThread();
        readThread.start();
        new Writethread().start();
        Thread.sleep(10);
        readThread.interrupt();
        System.out.println("main exit");
    }
}
