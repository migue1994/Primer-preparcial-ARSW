package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service
public class PrimeServiceStub implements PrimeService{

    private List<FoundPrime> primes = new ArrayList<>();

    @Override
    public void addFoundPrime( FoundPrime foundPrime ) throws PrimeException
    {
        synchronized(primes){
            boolean p = primes.stream().anyMatch(pm -> pm.getPrime() == foundPrime.getPrime());
            if(p){
                throw new PrimeException("EL número primo ya existe");
            }else{
                primes.add(foundPrime);
            }
        }
    }

    @Override
    public List<FoundPrime> getFoundPrimes()
    {  
        synchronized(primes){
            return this.primes;
        }
    }

    @Override
    public FoundPrime getPrime( String prime ) throws PrimeException
    {
        synchronized(primes){
            Optional<FoundPrime> fp = primes.stream().filter(prim -> prim.getPrime().equals(prime)).findFirst();
            if(fp == null){
                throw new PrimeException("El primo no existe");
            }
            return fp.get();
            // for(FoundPrime fp : primes){
            //     if (fp.getPrime().equals(prime)){
            //         return fp;
            //     }
            // }
            // throw new PrimeException("El primo no existe");
        }
    }
}
