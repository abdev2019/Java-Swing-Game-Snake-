package outils;

import java.awt.Color;


public class Cercle 
{  
    private Point centre; 
    private int rayon;    
    
    
    public Cercle(Point c, int r)
    {
        setRayon(r);
        setCentre(c);  
    }
    
    
    
    
    public void mover(Deplacement dep, Point next)
    {     
        getCentre().mouver(dep, next);
    }  
     
    public void setCentre(Point centre) {
        this.centre = new Point( centre.getX(),centre.getY() ); 
    }
    
    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public Point getCentre() {
        return centre;
    }

    public int getRayon() {
        return rayon;
    } 
     
    
    // graphic
    public void dessiner(java.awt.Graphics2D g, java.awt.Color clr){
        g.setColor(clr);
        g.fillOval( 
            (int)getCentre().getX(),
            (int)getCentre().getY(), 
            getRayon(), 
            getRayon()
        ); 
        g.setColor(Color.black);
        g.drawOval( 
            (int)getCentre().getX(),
            (int)getCentre().getY(), 
            getRayon(), 
            getRayon()
        ); 
    }
}
