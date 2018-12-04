
package outils;
 



public class Deplacement 
{ 
    private int vitesse;  
    private Point fin; 
     
     
    public Deplacement(int vitesse, Point fin)
    { 
        setVitesse(vitesse);    
        setFin( fin ); 
    }
    
    
    
 
     
    public Point getFin() {
        return fin;
    }

    public void setFin(Point fin) {
        this.fin = fin;
    }

    public void setVitesse(int vitesse) {
        if(vitesse<0) vitesse=0;
        this.vitesse = vitesse;
    }
  
    public int getVitesse() {
        return vitesse;
    } 
}
