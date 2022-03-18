package Chapter1;

/**
 * @ClassName ThreadTest
 * @Description 线程的三种创建方式
 * @Author lucius
 * @CreateTime 2022/3/16 21:52
 * @Version 1.0.0
 */

import java.util.concurrent.Callable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class ThreadTest {

    // 1.继承Thread类并重写run方法,优点是能够直接在run方法中获取当前进程资源，缺点是单继承
    public static class Mythread extends Thread {

        @Override
        public void run() {
            System.out.println("This is a test");
        }
    }

    // 2.实现Runable接口
    public static class RunableTask implements Runnable {

        @Override
        public void run() {
            System.out.println("This is a test");
        }
    }

    // 3.实现Callable接口 FutureTask有返回值
    public static class CallableTask implements Callable {

        @Override
        public Object call() throws Exception {
            return "hello world";
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //创建线程方法1
        Mythread thread = new Mythread();
        thread.start();

        //创建线程方法2
        RunableTask task = new RunableTask();
        new Thread(task).start();
        new Thread(task).start();

        //创建线程方法3
        // 首先创建一个FutureTask异步任务，构造函数是CallableTask
        FutureTask futureTask = new FutureTask<>(new CallableTask());
        // 启动线程
        new Thread(futureTask).start();
        try {
            // 等待线程执行完毕并返回结果
            String str = (String) futureTask.get();
            System.out.println(str);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
}
