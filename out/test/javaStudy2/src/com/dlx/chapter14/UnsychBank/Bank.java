package com.dlx.chapter14.UnsychBank;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final double[] accounts;
    //加入了ReentrantLock类
    //public Lock bankLock = new ReentrantLock();
    //private Condition sufficientFunds;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        //sufficientFunds = bankLock.newCondition();
    }

    public synchronized void transfer(int from, int to, double amount) {
       // bankLock.lock();
        try {
            if (accounts[to] < amount) {
                //
                wait();
            }
           // System.out.print(Thread.currentThread()+""+Thread.activeCount());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance: %10.2f%n", getTotalBalance());
            //sufficientFunds.signalAll();
            notifyAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //bankLock.unlock();
        }
    }

    public synchronized double getTotalBalance(){
        double sum = 0;

        for (double a : accounts) {
            sum += a;
        }

        return sum;
    }

    public int size(){
        return accounts.length;
    }
}
