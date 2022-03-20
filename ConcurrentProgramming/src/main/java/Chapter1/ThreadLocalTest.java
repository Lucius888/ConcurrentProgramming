package Chapter1;

/**
 * @ClassName ThreadLocalTest
 * @Description ThreadLocal 使用注意事项
 * 线程之间彼此不可见，操作的都是自己的备份内容。可仔细学习ThreadLocald的get()、set()、remove()方法源码;
 * 使用完毕后一定要remove,否则存在内存泄露的风险;
 * 同时其不具备继承性，因此子线程中无法获取父线程中的ThreadLacal值，这种情况需要使用InheritableThreadLocal;
 * 父线程必定也获取不到子线程的值
 * @Author lucius
 * @CreateTime 2022/3/17 15:01
 * @Version 1.0.0
 */
public class ThreadLocalTest {
    // 创建ThreadLocal变量
    static ThreadLocal<String> local = new ThreadLocal<>();

    static void print(String str) {
        System.out.println(str + ":" + local.get());
        //local.remove();
    }

    public static void main(String[] args) throws InterruptedException {
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("ThreadA local variable");
                print("ThreadA");
                System.out.println("ThreadA remove after " + ":" + local.get());
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                local.set("ThreadB local variable");
                print("ThreadB");
                System.out.println("ThreadB remove after " + ":" + local.get());
            }
        });

        threadA.start();
        System.out.println("mainThread get localVar" + ":" +local.get());
        threadB.start();
        System.out.println("mainThread get localVar" + ":" +local.get());

        threadA.join();
        threadB.join();

        // 继承性实验：在主线程中设置local,在子线程中获取
        local.set("inheritable test");
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 不具备继承性，因此获取的一定是null
                System.out.println("childThread get localVar" + ":" +local.get());
            }
        }).start();
    }
}
