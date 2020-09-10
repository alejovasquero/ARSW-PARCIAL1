package edu.eci.arsw.primefinder;

import edu.eci.arsw.math.MathUtilities;
import edu.eci.arsw.primefinder.thread.PrimeThread;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PrimeFinder{
        
	public static Object pauseLock = new Object();
    public static AtomicBoolean paused= new AtomicBoolean(true);
	public static AtomicInteger itCount = new AtomicInteger(0);
        
	public static void findPrimes(BigInteger _a, BigInteger _b, PrimesResultSet prs, int threads){
	    List<Thread> mimics = new ArrayList<Thread>();
	    BigInteger t = new BigInteger(String.valueOf(threads));
	    BigInteger n = _b.add(_a.multiply(new BigInteger(String.valueOf(-1))).add(BigInteger.ONE));
	    if(threads == 1){
	        mimics.add(new PrimeThread(_a, _b, prs));
        } else if(threads==2){
            mimics.add(new PrimeThread(_a,_a.add(n.divide(t)), prs));
            mimics.add(new PrimeThread(_a.add(n.divide(t)).add(BigInteger.ONE), _b, prs));
        } else {
            mimics.add(new PrimeThread(_a,_a.add(n.divide(t)), prs));
            mimics.add(new PrimeThread(_a.add(n.divide(t).multiply(t.add(new BigInteger(String.valueOf(-1))))).add(BigInteger.ONE), _b, prs));
            PrimeThread newer = null;
            BigInteger iBig = null;
            for (int i = 1; i<threads-1; i++){
                iBig =  new BigInteger(String.valueOf(i));
                newer = new PrimeThread(_a.add(n.divide(t).multiply(iBig)).add(BigInteger.ONE), _a.add(n.divide(t).multiply(iBig.add(BigInteger.ONE))), prs);
                mimics.add(newer);
            }
        }
        for(Thread th: mimics){
            th.start();
        }
	}


	
	public static void setPaused(boolean pause){
	    paused.set(pause);
	    if(!paused.get()){
	        synchronized (pauseLock){
                pauseLock.notifyAll();
            }
        }
    }
	
	
	
}
