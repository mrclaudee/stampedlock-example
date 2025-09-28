package locking;

import java.util.concurrent.locks.StampedLock;

public class BankAccount {
    private double balance = 0;
    private final StampedLock lock = new StampedLock();

    public void deposit(double amount) {
        long stamp = lock.writeLock();
        try {
            System.out.println(Thread.currentThread().getName() + " depositing: " + amount);
            balance += amount;
            System.out.println(Thread.currentThread().getName() + " new balance: " + balance);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public void withdraw(double amount) {
        long stamp = lock.writeLock();

        try {
            if (balance >= amount) {
                balance -= amount;
                System.out.println(Thread.currentThread().getName() + " withdrew:" + amount + ", New balance: " + balance);
            } else {
                System.out.println(Thread.currentThread().getName() + " withdrew:" + amount + ", Insufficient balance");
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    public double getBalanceOptimistic() {
        long stamp = lock.tryOptimisticRead();
        double currentBalance = balance;

        if (!lock.validate(stamp)) {
            try {
                stamp = lock.readLock();
                currentBalance = balance;
            } finally {
                lock.unlockRead(stamp);
            }
        }
        System.out.println(Thread.currentThread().getName() + " reading balance:" + currentBalance);
        return currentBalance;
    }


    public double getBalance() {
        long stamp = lock.readLock();
        try {
            return balance;
        }
        finally {
            lock.unlockRead(stamp);
        }
    }
}
