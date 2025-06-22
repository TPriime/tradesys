package com.agbafune.tradesys;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

public class UserLockManager {
    private static final ConcurrentHashMap<Long, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    public static void lockUser(Long userId) {
        lockMap.computeIfAbsent(userId, _ -> new ReentrantLock()).lock();
    }

    public static void unlockUser(Long userId) {
        ReentrantLock lock = lockMap.get(userId);
        if (lock != null && lock.isHeldByCurrentThread()) {
            lock.unlock();
        }
    }
}
