package edu.eci.arsw.primefinder;

import java.math.BigInteger;

import edu.eci.arsw.math.MathUtilities;

public class PrimeFinder extends Thread{
    
    private BigInteger _a;
    private BigInteger _b;
    private PrimesResultSet prs;
    private boolean stop = false;

    public PrimeFinder(BigInteger _a, BigInteger _b, PrimesResultSet prs){
        this._a=_a;
        this._b=_b;
        this.prs=prs;
    }

    @Override
    public void run(){
        // System.out.println("inicio");
        findPrimes(_a, _b, prs);
    }
        
	public void findPrimes(BigInteger _a, BigInteger _b, PrimesResultSet prs){
            
        BigInteger a=_a;
        BigInteger b=_b;

        MathUtilities mt=new MathUtilities();
        
        int itCount=0;
    
        BigInteger i=a;
        while (i.compareTo(b)<=0){
            itCount++;
            synchronized(prs){
                if (mt.isPrime(i)){
                    prs.addPrime(i);
                }
            }
            i=i.add(BigInteger.ONE);
        }
                
	}
	
}
