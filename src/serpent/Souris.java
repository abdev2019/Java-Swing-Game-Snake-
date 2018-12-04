/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serpent;

import System.IDessin;
import game.Fenetre;
import java.awt.Color;
import outils.Cercle;
import outils.Deplacement;
import outils.Ligne;
import outils.Point;
import outils.Rect;


public class Souris implements IDessin
{
    private Cercle tete;
    private Rect corps;
    private Ligne[] pattes;
    private Ligne queue;
    private Color coleur;
    private int taille;
    
    // deplacement
    private Deplacement dep; 
    java.util.Random rand = new java.util.Random(); 
    int tab[] = { -20,20 }; 



    
    public Souris(Point position, int taille, Color coleur) 
    { 
        setTaille(taille);
        init(position);
        this.coleur = coleur;
        this.setDeplacement( new Deplacement(1,new Point(200,400)) );
    }
    
    private void init(Point pos){ 
        creerTete(pos);
        creerCorps();
        creerPattes();
        creerQueue();
    }
    
    private void creerTete(Point position){
        int hTete = Fenetre.xToPercent(35, getTaille());
        this.setTete( new Cercle( position,hTete ) );
    }
    
    private void creerCorps(){
        Point base = new Point( 
            getTete().getCentre().getX()-getTaille()+getTete().getRayon()/4,
            getTete().getCentre().getY() + getTete().getRayon()-getTete().getRayon()/4
        );
        Rect rec = new Rect(base,getTaille(),getTaille()/3);
        this.setCorps(rec);
    }
    
    private void creerPattes(){
        this.setPattes( new Ligne[4] );
        for(int i=0;i<2;i++)
        {
            Point p1 = new Point( 
                getCorps().getBase().getX() + Fenetre.xToPercent(15, getTaille()),
                getCorps().getBase().getY() + getCorps().getHauteur()
            );
            Point p2 = new Point( 
                getCorps().getBase().getX() + Fenetre.xToPercent(15, getTaille()),
                getCorps().getBase().getY() + getCorps().getHauteur() + Fenetre.xToPercent(20, getTaille())
            );
            Ligne pat = new Ligne(p1,p2);
            this.getPattes()[i] = pat;
        }
        
        for(int i=2;i<4;i++)
        {
            Point p1 = new Point( 
                getCorps().getBase().getX() + getCorps().getLongueur()- Fenetre.xToPercent(15, getTaille()),
                getCorps().getBase().getY() + getCorps().getHauteur()
            );
            Point p2 = new Point( 
                getCorps().getBase().getX() +getCorps().getLongueur()- Fenetre.xToPercent(15, getTaille()),
                getCorps().getBase().getY() +getCorps().getHauteur() +  Fenetre.xToPercent(20, getTaille())
            );
            Ligne pat = new Ligne(p1,p2);
            this.getPattes()[i] = pat;
        } 
    }
    
    private void creerQueue(){
        Point p1 = new Point( 
            getCorps().getBase().getX() - Fenetre.xToPercent(20, getTaille()),
            getCorps().getBase().getY() - Fenetre.xToPercent(20, getTaille())
        );
        Point p2 = new Point( 
            getCorps().getBase().getX() ,
            getCorps().getBase().getY() 
        );
        Ligne q1 = new Ligne(p1,p2); 
        this.setQueue(q1);
    }

    
    public void mouver()
    {
        getTete().mover( getDeplacement(), getDeplacement().getFin() );
        this.getCorps().getBase().mouver(getDeplacement(), getDeplacement().getFin() );
        this.getQueue().getP1().mouver( getDeplacement(), getDeplacement().getFin() );
        this.getQueue().getP2().mouver( getDeplacement(), getDeplacement().getFin() );
        for(Ligne l : this.getPattes()){
            l.getP1().mouver( getDeplacement(), getDeplacement().getFin() ); 
            l.getP2().mouver( getDeplacement(), getDeplacement().getFin() ); 
        }
    }
    
    public void randomMove()
    { 
        getDeplacement().getFin().setY( 
            getDeplacement().getFin().getY()
            +
            tab[ rand.nextInt(2) ]
        );

        getDeplacement().getFin().setX( 
            getDeplacement().getFin().getX()
            +
            tab[ rand.nextInt(2) ]
        ); 
    }

    
    
    public Deplacement getDeplacement() {
        return dep;
    }

    public void setDeplacement(Deplacement dep) {
        this.dep = dep;
    }
    
    
    
    public int getTaille() {
        return taille;
    }

    public void setTaille(int taille) {
        this.taille = taille;
    }
     
    public Cercle getTete() {
        return tete;
    }

    public void setTete(Cercle tete) {
        this.tete = tete;
    }

    public Rect getCorps() {
        return corps;
    }

    public void setCorps(Rect corps) {
        this.corps = corps;
    }

    public Ligne[] getPattes() {
        return pattes;
    }

    public void setPattes(Ligne[] pattes) {
        this.pattes = pattes;
    }

    public Ligne getQueue() {
        return queue;
    }

    public void setQueue(Ligne dl) {
        this.queue = dl;
    }

    public Color getColeur() {
        return coleur;
    }

    public void setColeur(Color coleur) {
        this.coleur = coleur;
    }

    
    
    
    @Override public void dessiner(java.awt.Graphics2D g){  
        this.getTete().dessiner(g, getColeur());
        
        g.setColor(getColeur());
        g.fillRect(
            (int)getCorps().getBase().getX(),
            (int)getCorps().getBase().getY(),
            getCorps().getLongueur(), 
            getCorps().getHauteur()
        );
        g.setColor(Color.black);
        g.drawRect(
            (int)getCorps().getBase().getX(),
            (int)getCorps().getBase().getY(),
            getCorps().getLongueur(), 
            getCorps().getHauteur()
        );
        
        g.setColor(getColeur());
        for( Ligne patte : getPattes() )
            g.drawLine(
                (int)patte.getP1().getX(), 
                (int)patte.getP1().getY(), 
                (int)patte.getP2().getX(), 
                (int)patte.getP2().getY()
            );
        
        g.drawLine(
            (int)getQueue().getP1().getX(), 
            (int)getQueue().getP1().getY(), 
            (int)getQueue().getP2().getX(), 
            (int)getQueue().getP2().getY()
        );
        
    }
    
}
