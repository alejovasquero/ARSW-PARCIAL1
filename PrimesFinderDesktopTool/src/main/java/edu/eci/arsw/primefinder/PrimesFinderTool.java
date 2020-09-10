package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.eci.arsw.primefinder.thread.PauseThread;
import edu.eci.arsw.primefinder.thread.PrimeThread;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool {
    public static Object pauseLock = new Object();
	public static void main(String[] args) {
		            
            int maxPrim=1000;
            
            PrimesResultSet prs=new PrimesResultSet("john");
            PrimeFinder.setPaused(true);
            PrimeFinder.findPrimes(new BigInteger("1"), new BigInteger("10000"), prs, 4);
            System.out.println("Prime numbers found:");
            System.out.println(prs.getPrimes());
            PauseThread pt = new PauseThread();
            pt.start();
            synchronized (pauseLock){
                try {
                    pauseLock.wait();
                } catch (InterruptedException e) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, e);
                }
            }
            PrimeFinder.setPaused(false);
            while(PrimeFinder.threadsDone.get()!=0){
                try {
                    PrimeFinder.setPaused(false);
                    synchronized (pauseLock){
                        pauseLock.wait();
                    }
                    PrimeFinder.setPaused(true);
                    System.out.println("Prime numbers found:");
                    System.out.println(prs.getPrimes());
                    System.out.println("User working again!");
                    PrimeFinder.setPaused(false);
                } catch (InterruptedException ex) {
                    Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
	}
}


