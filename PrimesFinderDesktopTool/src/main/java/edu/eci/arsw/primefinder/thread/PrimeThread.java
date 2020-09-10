package edu.eci.arsw.primefinder.thread;

import edu.eci.arsw.math.MathUtilities;
import edu.eci.arsw.primefinder.PrimeFinder;
import edu.eci.arsw.primefinder.PrimesResultSet;

import java.math.BigInteger;

public class PrimeThread extends Thread{

    private BigInteger inf;
    private BigInteger sup;
    private PrimesResultSet resultSet;

    public PrimeThread(BigInteger a, BigInteger b, PrimesResultSet set){
        this.inf = a;
        this.sup = b;
        this.resultSet = set;
    }

    @Override
    public void run() {
        BigInteger a=inf;
        BigInteger b=sup;
        MathUtilities mt=new MathUtilities();
        BigInteger i=a;
        while (i.compareTo(b)<=0){
            PrimeFinder.itCount.incrementAndGet();
            if (mt.isPrime(i)){
                resultSet.addPrime(i);
            }
            i=i.add(BigInteger.ONE);
        }
    }

    public void checkPause(){
        if(PrimeFinder.paused.get()){
            synchronized (PrimeFinder.pauseLock){
                try {
                    System.out.println("PAUSANDO");
                    PrimeFinder.pauseLock.wait();
                    System.out.println("CONTINUANDO");
                } catch (InterruptedException e) {
                    //e.printStackTrace();
                }
            }
        }
    }
}
