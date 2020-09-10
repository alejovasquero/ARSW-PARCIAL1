package edu.eci.arsw.primefinder.thread;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import edu.eci.arsw.primefinder.PrimeFinder;
import edu.eci.arsw.primefinder.PrimesFinderTool;
import org.apache.http.annotation.Obsolete;

public class PauseThread extends Thread{

    @Override
    public void run() {
        while(PrimeFinder.threadsDone.get()!=0){
            if(MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>10000){
                synchronized (PrimesFinderTool.pauseLock){
                    PrimesFinderTool.pauseLock.notify();
                }
            }
        }
    }
}
