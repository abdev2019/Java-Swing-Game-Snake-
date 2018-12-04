package serpent;

import java.awt.Color;
import outils.Point;
import outils.Cercle;





public class Anneau extends Cercle
{  
    private java.awt.Color color; 
    private Point next; 
    
    
    public Anneau(Point c, int r,  java.awt.Color color) {
        super(c, r);
        this.setColor(color);
        this.setNext(null);
    }
    
    public Anneau(Anneau a) {
        super(a.getCentre(), a.getRayon());
        this.setColor(a.getColor());
    }

    public java.awt.Color getColor() {
        return color;
    }

    public void setColor(java.awt.Color color) {
        this.color = color;
    }
      
    public void setNext(Point next) {
        if(next==null) return;
        this.next = new Point(next);
    }
     
    public Point getNext() {
        return next;
    } 
    
    
    // graphic
    public void dessiner(java.awt.Graphics2D g){
        super.dessiner(g, getColor());
        g.setColor(Color.black);
        g.drawOval(
            (int)this.getCentre().getX()-1, 
            (int)this.getCentre().getY()-1, 
            this.getRayon()+2, 
            this.getRayon()+2
        );
    }
 
}
