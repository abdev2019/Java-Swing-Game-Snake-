/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

/**
 *
 * @author ABDO
 */


public class Mission 
{
    private Personne personne;
    private Etage etageAdeplacer; 
    private boolean arrive = false;

    public Mission() {
    }

    
    public Mission(Personne personne, Etage etageAdeplacer) {
        this.personne = personne;
        this.etageAdeplacer = etageAdeplacer;
    }

    
    
    public boolean isArrive() {
        return arrive;
    }

    public void setArrive(boolean arrive) {
        this.arrive = arrive;
    }
    
    

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne p) {
        this.personne = p;
    }

    public Etage getEtageAdeplacer() {
        return etageAdeplacer;
    }

    public void setEtageAdeplacer(Etage etageAdeplacer) {
        this.etageAdeplacer = etageAdeplacer;
    }
    
}
