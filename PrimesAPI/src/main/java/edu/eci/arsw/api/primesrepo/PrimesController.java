package edu.eci.arsw.api.primesrepo;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.PrimeException;
import edu.eci.arsw.api.primesrepo.service.PrimeService;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RestController
@RequestMapping("/primes")
public class PrimesController
{

    @Autowired
    PrimeService primeService;


    @GetMapping("")
    public ResponseEntity<?> getFoundPrimes(){
        return new ResponseEntity<>(primeService.getFoundPrimes(), HttpStatus.ACCEPTED);
    }

    @PostMapping
    public ResponseEntity<?> postBlueprints(@Valid @RequestBody FoundPrime fp) throws PrimeException {
        try{
            primeService.addFoundPrime(fp);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>("No se pudo crear",HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping("/{primenumber}")
    public ResponseEntity<?> getFoundPrime(@PathVariable String primenumber){
        try{
            return new ResponseEntity<>(primeService.getPrime(primenumber), HttpStatus.ACCEPTED);
        }catch(PrimeException pe){
            return new ResponseEntity<>("No existe el número solicitado", HttpStatus.NOT_FOUND);
        }
    }
}
