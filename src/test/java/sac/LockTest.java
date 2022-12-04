package sac;

import org.junit.jupiter.api.Test;
import sac.utils.Lock;

import static org.junit.jupiter.api.Assertions.*;

public class LockTest {
    @Test
    void testInit() {
        Lock lock = new Lock();
        assertFalse(lock.isStarted());
        assertFalse(lock.isLocked());
    }

    @Test
    void testLock() throws InterruptedException {
        Lock lock = new Lock();
        lock.lock(200);
        assertTrue(lock.isStarted());
        assertTrue(lock.isLocked());
        Thread.sleep(300);
        assertTrue(lock.isStarted());
        assertFalse(lock.isLocked());
    }

    @Test
    void testAutoUnlock() throws InterruptedException {
        Lock lock = new Lock();
        lock.lock(1);
        Thread.sleep(10);
        assertTrue(lock.isStarted());
        assertFalse(lock.isLocked());
    }

    @Test
    void testManualUnlock() {
        Lock lock = new Lock();
        lock.lock(20000);
        lock.unlock();
        assertFalse(lock.isStarted());
        assertFalse(lock.isLocked());
    }

    @Test
    void testZeroDelay() throws InterruptedException {
        Lock lock = new Lock();
        lock.lock(0);
        Thread.sleep(1);
        assertTrue(lock.isStarted());
        assertFalse(lock.isLocked());
    }
}
