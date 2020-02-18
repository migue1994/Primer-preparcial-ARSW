package edu.eci.arsw.primefinder;

import edu.eci.arsw.mouseutils.MouseMovementMonitor;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class PrimesFinderTool {
    public static List<PrimeFinder> primefinders = new ArrayList<>();
    public static void main(String[] args) {

        int maxPrim = 100;

        PrimesResultSet prs = new PrimesResultSet("john");
        
        int inicio = 0;
        int intervalo = maxPrim / 4;
        int fin = intervalo;
        for (int i = 0; i < 4; i++) {
            primefinders.add(
                    new PrimeFinder(new BigInteger(String.valueOf(inicio)), new BigInteger(String.valueOf(fin)), prs));
            inicio += intervalo;
            fin += intervalo;
        }

        primefinders.forEach(pf -> pf.start());

        // puntoDos();

        for (PrimeFinder pf : primefinders) {
            try {
                pf.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }



        System.out.println("Prime numbers found:");
        
        System.out.println(prs.getPrimes());
    }

    public static void puntoDos(){
        while(task_no_finish()){
            try { 
                Thread.sleep(10);
                if (MouseMovementMonitor.getInstance().getTimeSinceLastMouseMovement()>1000){
                    for(PrimeFinder pf : primefinders){
                        synchronized(pf){
                            pf.notify();
                        }
                    }
                }
                else{
                    for(PrimeFinder pf : primefinders){
                        synchronized(pf){
                            pf.wait();
                        }
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(PrimesFinderTool.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
	public static boolean task_no_finish(){
        for(PrimeFinder pf:primefinders){
            if(pf.isAlive()){
                return true;
            }
        }
        return false;
    }
}


