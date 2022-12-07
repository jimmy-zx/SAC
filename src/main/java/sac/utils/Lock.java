package sac.utils;

/**
 * A static lock for a specific delay
 * <p>
 * A Locker has three states:
 * <ul>
 *     <li>not started</li>
 *     <li>started and locked (non-expired)</li>
 *     <li>started and unlocked (expired)</li>
 * </ul>
 */
public class Lock {
    private long lockerEndTime;

    /**
     * Initializes a new Lock
     */
    public Lock() {
        lockerEndTime = -1;
    }

    /**
     * Lock for a specific delay.
     * <p>
     * The lock will automatically expire after delayMills.
     *
     * @param delayMills The time to lock the Lock.
     */
    public void lock(int delayMills) {
        lockerEndTime = System.currentTimeMillis() + delayMills;
    }

    /**
     * Check whether there is a lock.
     *
     * @return Whether the user has called lock()
     */
    public boolean isStarted() {
        return lockerEndTime >= 0;
    }

    /**
     * Check whether there is a non-expired lock.
     *
     * @return Whether there is a valid lock, and the lock isn't expired.
     */
    public boolean isLocked() {
        if (!isStarted()) {
            return false;
        }
        return System.currentTimeMillis() <= lockerEndTime;
    }

    /**
     * Remove the lock.
     */
    public void unlock() {
        lockerEndTime = -1;
    }
}
