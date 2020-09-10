package edu.eci.arsw.api.primesrepo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.eci.arsw.api.primesrepo.model.FoundPrime;
import edu.eci.arsw.api.primesrepo.service.PrimeService;
import org.apache.coyote.Response;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Santiago Carrillo
 * 2/22/18.
 */
@RestController
public class PrimesController
{
    @Autowired
    PrimeService primeService;

    private ObjectMapper mapper = new ObjectMapper();

    @RequestMapping( value = "/primes", method = GET )
    public ResponseEntity<?> getPrimes() {
        try {
            return new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(primeService.getFoundPrimes()), HttpStatus.OK);
        } catch (JsonProcessingException e) {
            return new ResponseEntity<>("500 InternalServer error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping( value = "/primes/{primenumber}", method = POST )
    public ResponseEntity<?> postPrimes(@PathVariable String primenumber, @RequestBody String body) {
        FoundPrime current = primeService.getPrime(primenumber);
        FoundPrime
        if(current != null  && !current.getPrime().equals(primenumber)){
            return new ResponseEntity<>("400 Bad Request", HttpStatus.BAD_REQUEST);
        }
        FoundPrime insert = mapper.readValue()
    }

    @RequestMapping( value = "/primes/{primenumber}", method = GET )
    public ResponseEntity<?> getPrime(@PathVariable String primenumber) {
        FoundPrime ans = primeService.getPrime(primenumber);
        ResponseEntity<?> response = null;
        if(ans == null){
            response = new ResponseEntity<>("404 Not Found", HttpStatus.NOT_FOUND);
        } else {
            try {
                response = new ResponseEntity<>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(primeService.getPrime(primenumber)), HttpStatus.OK);
            } catch (JsonProcessingException e) {
                response = new ResponseEntity<>("500 InternalServer error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return response;
    }
}
