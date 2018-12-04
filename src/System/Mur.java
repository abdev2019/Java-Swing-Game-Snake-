package System;

import java.awt.Color;
import outils.Point;
import outils.Rect;




public class Mur extends Rect implements IDessin
{
    private Color coleur;

    public Mur( Point base, int w, int h, Color coleur) {
        super(base, w, h);
        this.coleur = coleur;
    }

    
    
    
    public Color getColeur() {
        return coleur;
    }

    public void setColeur(Color coleur) {
        this.coleur = coleur;
    }
  
    @Override public void dessiner(java.awt.Graphics2D g){ 
        g.setColor( getColeur() );
        g.fillRect((int)getBase().getX(), (int)getBase().getY(), getLongueur(), getHauteur()); 
    }
}
