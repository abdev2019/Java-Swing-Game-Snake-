package System;

import java.awt.Color;
import outils.Point;
import outils.Rect;



public class Ascenceur extends Rect implements IDessin
{
    private Color coleur;
    private Cabinet cabinet;
    

    public Ascenceur(Point base, int w, int h, Color coleur) {
        super(base, w, h);
        this.coleur = coleur; 
    }
    

    
    public Color getColeur() {
        return coleur;
    }

    public void setColeur(Color coleur) {
        this.coleur = coleur;
    }

    public Cabinet getCabinet() {
        return cabinet;
    }

    public void setCabinet(Cabinet cabinet) {
        this.cabinet = cabinet;
    }
    
 
    @Override public void dessiner(java.awt.Graphics2D g)
    { 
        //g.setColor( getColeur() );
        //g.drawRect((int)getBase().getX(), (int)getBase().getY(), getLongueur(), getHauteur());
        
        getCabinet().dessiner(g);
    }
}
