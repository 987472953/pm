import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author QYstart
 * @date 2021/4/15
 */
public class Test {
    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        lock.unlock();

//        Semaphore semaphore = new Semaphore(3);
//        semaphore.acquire();
//        semaphore.acquire();
//        semaphore.acquire();
//        semaphore.acquire();
//        semaphore.release();


        LinkedBlockingDeque<Integer> integers = new LinkedBlockingDeque<>();

    }
}
