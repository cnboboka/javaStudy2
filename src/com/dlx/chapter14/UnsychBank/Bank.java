package com.dlx.chapter14.UnsychBank;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bank {
    private final double[] accounts;
    //加入了ReentrantLock类
    public Lock bankLock;
    public Condition sufficientFunds;

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) {
        bankLock.lock();
        try {
            if (accounts[to] < amount) {
                sufficientFunds.await();
            }
            System.out.print(Thread.currentThread());
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" . Total Balance: %10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance(){

        bankLock.lock();
        try{
            double sum = 0;
            for (double a : accounts) {
                sum += a;
            }

            return sum;
        }
        finally {
            bankLock.unlock();
        }


    }

    public int size(){
        return accounts.length;
    }
}
