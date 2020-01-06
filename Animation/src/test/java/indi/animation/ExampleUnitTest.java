package indi.animation;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void Test() {
//
//        final A a = new A();
//        final A aa = new A();
//
//        ExecutorService es = Executors.newFixedThreadPool(3);
//        es.submit(new Runnable() {
//            @Override
//            public void run() {
//                a.printA();
//
//            }
//        });
//        es.submit(new Runnable() {
//            @Override
//            public void run() {
//                a.printB();
//            }
//        });
//        es.submit(new Runnable() {
//            @Override
//            public void run() {
//                a.printC();
//            }
//        });
//        es.shutdown();

        System.out.println((1<<1)+1);
    }

}

class A {

    C c;

    public A() {
        this.c = new C();
    }

    public synchronized static void printA() {
        for (int i = 0; i < 100; i++) {
            System.out.println("AAA:" + "第" + i + "次————" + Thread.currentThread().getName());
        }
    }

    public void printB() {
        synchronized (A.class) {
            for (int i = 0; i < 100; i++) {
                System.out.println("BBB:" + "第" + i + "次————" + Thread.currentThread().getName());
            }
        }
    }

    public synchronized void printC() {
        c.printC();
    }


}

class C {
    public void printC() {
        for (int i = 0; i < 100; i++) {
            System.out.println("CCC:" + "第" + i + "次————" + Thread.currentThread().getName());
        }
    }
}