package System;

import java.awt.Color;
import java.util.ArrayList;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:42
 */
public class Etage extends Rect implements IDessin
{ 
    private Color coleur;
    public ArrayList<Personne> personnes;
    private Porte porte;
    private Batiment batiment;
    private int num;
    


    public Etage( Point base, int hauteur, int longueur, Color coleur) {
        super(base, hauteur, longueur);
        this.coleur = coleur;
        setPersonnes(new ArrayList()); 
    }

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    
    
    
    
    public Porte getPorte() {
        return porte;
    }

    public void setPorte(Porte porte) {
        this.porte = porte;
    }
    


    public ArrayList<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(ArrayList<Personne> personnes) {
        this.personnes = personnes;
    }

    public Color getColeur(){
            return coleur;
    } 
    
    public void setColeur(Color newVal){
            coleur = newVal;
    }
    
    @Override public void dessiner(java.awt.Graphics2D g){ 
        g.setColor( getColeur() );
        g.fillRect((int)getBase().getX(), (int)getBase().getY(), getLongueur(), getHauteur());
        getPorte().dessiner(g);
                
        g.setColor(Color.black);
        g.fillPolygon(
            new int[] {
                (int)getPorte().getBase().getX()- 100, 
                (int)getPorte().getBase().getX(), 
                (int)getPorte().getBase().getX() 
            }, 
            new int[] {
                (int)( getBase().getY() + 10 ), 
                (int)( getBase().getY() + 10 ) , 
                (int)(getBase().getY()+ getPorte().getHauteur()+10 )
            }, 
            3
        ); 
    }

}