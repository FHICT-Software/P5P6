/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package movingballsfx;

import java.util.concurrent.locks.*;

/**
 *
 * @author anne
 */
public class MonitorOPD1 implements IMonitor{

    private Lock monLock;
    private int writersActive;
    private int readersActive;
    private Condition okToRead;
    private Condition okToWrite;
    private int readersWaiting;

    public MonitorOPD1() {
        this.monLock = new ReentrantLock();
        this.writersActive = 0;
        this.readersActive = 0;
        this.readersWaiting = 0;
        this.okToRead = monLock.newCondition();
        this.okToWrite = monLock.newCondition();
    }

    @Override
    public void enterReader() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive != 0) {
                readersWaiting++;
                okToRead.await();
                readersWaiting--;
            }
            readersActive++;
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public void exitReader() {
        monLock.lock();
        try {
            readersActive--;
            if (readersActive == 0) {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public void enterWriter() throws InterruptedException {
        monLock.lock();
        try {
            while (writersActive > 0 || readersActive > 0) {
                okToWrite.await();
            }
            writersActive++;
        } finally {
            monLock.unlock();
        }
    }

    @Override
    public void exitWriter() {
        monLock.lock();
        try {
            writersActive--;
            if (readersWaiting > 0) {
                okToRead.signalAll();
            } else {
                okToWrite.signal();
            }
        } finally {
            monLock.unlock();
        }
    }

}
