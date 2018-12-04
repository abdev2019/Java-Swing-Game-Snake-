package System;

import java.awt.Color;
import outils.Deplacement;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:42
 */
public class Porte extends Rect implements IDessin
{
    private Color coleur;
    private boolean ouvert; 
    private Etage etage;


    public Porte( Point base, int hauteur, int longueur, Color coleur) {
        super(base, hauteur, longueur);
        this.coleur = coleur;
        this.setOuvert(false);
    }

    

    public void ouvrir()
    {
        if( !isOuvert() )
        {
            new Thread(new Runnable(){ @Override public void run()
            { 
                if( getEtage()==null ) return;
                Point p = new Point( getBase().getX(), ( getEtage().getBase().getY() - 2*getHauteur()+10/2 +1   ) );
                Deplacement dep = new Deplacement(2,p);
                while(true)
                {
                    getBase().mouver(dep, p);
                    if(getBase().identique(dep.getFin(),1f))  {
                        setOuvert(true); 
                        return;
                    }
                    try{ Thread.sleep(17); }catch(Exception e){}
                }
            }}).start();
        }
        else fermer();
    }
    
    public void fermer(){
        if( isOuvert() )
        {
            new Thread(new Runnable(){ @Override public void run()
            { 
                Point p = new Point( getBase().getX(), ( getEtage().getBase().getY() - getHauteur()  ) );
                Deplacement dep = new Deplacement(2,p);
                while(true)
                {
                    getBase().mouver(dep, p);
                    if(getBase().identique(dep.getFin(),1))  {
                        setOuvert(false);
                        setColeur(Color.green);
                        return;
                    }
                    try{ Thread.sleep(17); }catch(Exception e){}
                }
            }}).start();
        }
    }

    public Etage getEtage() {
        return etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }

    
    
    public Color getColeur(){
            return coleur;
    }

    public boolean isOuvert(){
            return ouvert;
    }
 
    public void setColeur(Color newVal){
            coleur = newVal;
    }
 
    public void setOuvert(boolean newVal){
            ouvert = newVal;
    }
 
    @Override public void dessiner(java.awt.Graphics2D g){ 
        g.setColor( getColeur() );
        g.fillRect((int)getBase().getX(), (int)getBase().getY(), getLongueur(), getHauteur());
        g.setColor(Color.black);
        g.drawLine((int)getBase().getX()+getLongueur()/2, (int)getBase().getY(), (int)getBase().getX()+getLongueur()/2, (int)getBase().getY()+getHauteur());
        g.drawLine((int)getBase().getX(), (int)getBase().getY()+getHauteur()/2, (int)getBase().getX()+getLongueur(), (int)getBase().getY()+getHauteur()/2);
    }
}