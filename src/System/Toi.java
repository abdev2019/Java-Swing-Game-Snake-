package System;

import java.awt.Color;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:42
 */
public class Toi extends Rect  implements IDessin
{
    private Color coleur;
    private Batiment batiment;
  

    public Toi(Color coleur, int longueur , int hauteur, Point base) {
        super(base, hauteur, longueur);
        this.coleur = coleur;
    }

    
    
    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }
        
    
    
    public Color getColeur(){
            return coleur;
    }
 
    public void setColeur(Color newVal){
            coleur = newVal;
    }
 
    @Override public void dessiner(java.awt.Graphics2D g){ 
        g.setColor( getColeur() );
        g.fillRect((int)getBase().getX(), (int)getBase().getY(),  getLongueur(), getHauteur());
        
        
        
        int ht = (getBatiment().getMur().getHauteur()-10)/(getBatiment().getNbrEtages()+1);
        
        int h = (batiment.getMur().getHauteur()-10)/(batiment.getNbrEtages()+1);
        h = h/2;
        
        g.fillPolygon(
            new int[] {
                (int)getBase().getX() + getLongueur()-120, 
                (int)getBase().getX() + getLongueur()-220, 
                (int)getBase().getX() + getLongueur()-120
            }, 
            new int[] {
                (int)( getBase().getY() + getHauteur()  ), 
                (int)( getBase().getY() + getHauteur()  ) , 
                (int)(getBase().getY()+ h +10  )
            }, 
            3
        ); 
    }
}