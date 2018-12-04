package System;

import java.awt.Color;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:42
 */
public class Rdc extends Etage implements IDessin
{  

    public Rdc(Point base, int hauteur, int longueur, Color coleur) {
        super(base, hauteur, longueur, coleur);
        //this.coleur = coleur;
    }

    
/*
    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }
    
    public Color getColeur(){
            return coleur;
    }
 
    public void setColeur(Color newVal){
            coleur = newVal;
    }
 */
    @Override public void dessiner(java.awt.Graphics2D g){ 
        g.setColor( getColeur() );
        g.fillRect((int)getBase().getX(), (int)getBase().getY(), getLongueur(), getHauteur());
        getPorte().dessiner(g);
    }
}