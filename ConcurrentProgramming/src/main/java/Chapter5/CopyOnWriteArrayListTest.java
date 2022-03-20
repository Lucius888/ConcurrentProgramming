package Chapter5;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName CopyOnWriteArrayListTest
 * @Description CopyOnWriteArrayList 使用注意事项
 * 通过独享锁保证list 的增删改线程安全，通过写时复制提供数据的弱一致性
 * 下面的代码就非常清晰的展示了迭代器的弱一致性，获取的都是版本快照
 * @Author lucius
 * @CreateTime 2022/3/20 20:08
 * @Version 1.0.0
 */
public class CopyOnWriteArrayListTest {
    private static volatile CopyOnWriteArrayList<String> copyOnWriteArrayList = new CopyOnWriteArrayList<>();

    public static void main(String[] args) throws InterruptedException {
        copyOnWriteArrayList.add("hello");
        copyOnWriteArrayList.add("alibaba");
        copyOnWriteArrayList.add("welcome");
        copyOnWriteArrayList.add("to");
        copyOnWriteArrayList.add("hangzhou");
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                copyOnWriteArrayList.set(1, "wangyi");
                copyOnWriteArrayList.remove(2);
                copyOnWriteArrayList.remove(3);
            }
        });

        // 保证在修改线程启动之前获取list的迭代器
        Iterator<String> iterator = copyOnWriteArrayList.iterator();
        threadOne.start();
        threadOne.join();
        while (iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
}
