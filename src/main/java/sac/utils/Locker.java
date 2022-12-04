package sac.utils;

public class Locker {
    long lockerEndTime;

    public Locker() {
        lockerEndTime = -1;
    }

    public void lock(int delayMills) {
        lockerEndTime = System.currentTimeMillis() + delayMills;
    }

    public boolean isLocked() {
        return lockerEndTime >= 0;
    }

    public boolean isExpired() {
        if (!isLocked()) {
            return false;
        }
        return System.currentTimeMillis() <= lockerEndTime;
    }

    public void unlock() {
        lockerEndTime = -1;
    }
}
