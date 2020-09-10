package edu.eci.arsw.api.primesrepo.service;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@Service
public class PrimeServiceStub implements PrimeService {

    private ConcurrentHashMap<String , FoundPrime> primes = new ConcurrentHashMap<>();

    @Override
    public void addFoundPrime( FoundPrime foundPrime )
    {
        primes.put(foundPrime.getPrime(), foundPrime);
    }

    @Override
    public List<FoundPrime> getFoundPrimes()
    {
        return new ArrayList<>(primes.values());
    }

    @Override
    public FoundPrime getPrime( String prime )
    {
        if(prime.contains(prime)){
            return primes.get(prime);
        }
        return null;
    }
}
