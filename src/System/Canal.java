/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.awt.Color;
import java.awt.Graphics2D;
import outils.Cercle;
import outils.Point;
import outils.Rect;
 
public class Canal implements IDessin
{
    private Rect bonde;
    private Cercle entre;
    private Cercle sortie;
    private Color coleur;
    private int taille; 

    
    public Canal(Point pos, int taille, Color coleur) {
        this.coleur = coleur;
        this.setTaille(taille);
        init(pos);
    }
    
    private void init(Point pos)
    {
        int ray = 35;
        this.entre = new Cercle( new Point(pos.getX()+5,pos.getY()),ray );
        this.sortie = new Cercle( new Point(pos.getX()+5,pos.getY()+this.getTaille()-ray),ray );
        
        this.bonde = new Rect( 
            new Point(this.entre.getCentre().getX()+1,this.entre.getCentre().getY()+ray/2),
            ray-2,
            this.getTaille()-ray+1
        );
    }

    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }

    
    
    
    public Rect getBonde() {
        return bonde;
    }

    public void setBonde(Rect bonde) {
        this.bonde = bonde;
    }

    public Cercle getEntre() {
        return entre;
    }

    public void setEntre(Cercle entre) {
        this.entre = entre;
    }

    public Cercle getSortie() {
        return sortie;
    }

    public void setSortie(Cercle sortie) {
        this.sortie = sortie;
    }

    public Color getColeur() {
        return coleur;
    }

    public void setColeur(Color coleur) {
        this.coleur = coleur;
    }

    @Override
    public void dessiner(Graphics2D g) { 
        g.setColor(coleur);
        g.fillRect(
            (int)getBonde().getBase().getX(), 
            (int)getBonde().getBase().getY(), 
            getBonde().getLongueur(), 
            getBonde().getHauteur()
        );
        getEntre().dessiner(g, Color.black);
        getSortie().dessiner(g, Color.black);
    }
    
    
    
}
