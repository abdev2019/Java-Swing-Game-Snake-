package System;

import game.Fenetre;
import java.awt.Color;
import java.util.ArrayList;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:41
 */
public class Batiment extends Rect  implements IDessin
{ 
    private Color coleur;
    private Rdc rdc;
    private Mur mur;
    private Toi toi;
    private Ascenceur ascenceur;
    private ArrayList<Etage> etages;
    private int nbrEtages = 0;
     
    
    
    public Batiment(int hauteur, int longueur, Point base, Color coleur, int nbrEtanges) {
        super(base,hauteur, longueur);  
        this.setColeur(coleur);
        this.setNbrEtages(nbrEtanges); 
        this.setEtages(new ArrayList()); 
        
        this.setRdc(creerRcd());
        setMur( creerMur() ); 
        
        this.setToi(creerToi());
        this.getToi().setBatiment(this);
        
        this.setAscenceur( creerAscenceur() );
        
        creerEtages();
         
        Porte porte = creerPorteEtage(); 
        porte.setBase( new Point( 
                getAscenceur().getBase().getX()-porte.getLongueur(),
                getRdc().getBase().getY()-porte.getHauteur() 
        ) );
        porte.setEtage(getRdc());
        getRdc().setPorte( porte );
        
        this.getAscenceur().setCabinet( this.creerCabinet() );
        this.getAscenceur().getCabinet().setPorte( creerPorteAscenceur() );
        this.getAscenceur().getCabinet().setEtage(getRdc());
        this.getAscenceur().getCabinet().setBatiment(this);
    }
    
    
    
    private Rdc creerRcd()
    { 
        Point base = new Point(50,getHauteur()-50);
        int longueur = getLongueur()-50;
        int hauteur = 50;
        Rdc rdc = new Rdc(  base, longueur, hauteur, new Color(170,70,70) ); 
        rdc.setNum(0);
        this.getEtages().add(rdc);
        return rdc; 
    }   
    
    
    private Mur creerMur(){
        int longueur = 10;
        int hauteur = Fenetre.xToPercent(80,getHauteur());
        
        Point base = new Point(
                getRdc().getBase().getX() + getRdc().getLongueur()-5*longueur, 
                getRdc().getBase().getY() -hauteur  );
        
        return new Mur(base, longueur, hauteur,  Color.blue ); 
    } //ok
    
    private Toi creerToi(){  
        int longueur = getRdc().getLongueur()/2+10;
        int hauteur = 10;
        
        Point base = new Point( 
            getMur().getBase().getX()-longueur+10 ,
            getMur().getBase().getY()
        );
        return new Toi( Color.red, hauteur, longueur, base ); 
    } //ok
    
    private void creerEtages()
    {   
        int longueur = getRdc().getLongueur()/2;
        int hauteur  = (getMur().getHauteur())/(getNbrEtages()+1);

        for(int i=1;i<=getNbrEtages();i++)
        {
            Point base =    new Point( 
                                getMur().getBase().getX()-longueur ,
                                (getMur().getBase().getY()+getMur().getHauteur())-i*(hauteur)
                            );
            Etage etg = new Etage( base, longueur, 10, Color.black );
            etg.setNum(i);
            
            Porte porte = creerPorteEtage();
            Point positionPorte = new Point(
                    getAscenceur().getBase().getX()-porte.getLongueur() , 
                    base.getY()-porte.getHauteur()
            );
            porte.setBase( positionPorte );
            porte.setEtage(etg);
            
            etg.setPorte( porte );
            etg.setBatiment(this);
            getEtages().add( etg );
        } 
    }
    private Porte creerPorteEtage()
    {
        int w = 10;
        int h = (getMur().getHauteur()-10)/(getNbrEtages()+1);
        h = h/2;
        return new Porte(null, w, h, Color.green);
    }
   
    
    private Ascenceur creerAscenceur(){
        
        int longueur = 100;
        int hauteur  = getMur().getHauteur() ;

        Point base = new Point( getMur().getBase().getX()-100, getMur().getBase().getY() );
        Ascenceur asc = new Ascenceur(base,longueur,hauteur , Color.black);
        return asc;
    }
     
    
    private Cabinet creerCabinet(){  
        int hauteur   = (getMur().getHauteur()-10)/(getNbrEtages()+1);
        int longueur  = getAscenceur().getLongueur()-10;
        
        int ht = Fenetre.xToPercent(70, hauteur);
        
        hauteur = hauteur -hauteur/(getNbrEtages()+1)-20;
        
        Point base = new Point(
            getAscenceur().getBase().getX()+5, 
            getAscenceur().getBase().getY() + getAscenceur().getHauteur() - ht 
        );
        return new Cabinet(longueur  ,ht  , base, Color.gray ); 
    } 
    
    private Porte creerPorteAscenceur(){  
        int hauteur = getAscenceur().getCabinet().getHauteur() -10;
        int longueur = getAscenceur().getCabinet().getLongueur() - 20;
        Point base = new Point(
            getAscenceur().getCabinet().getBase().getX()+10,
            getAscenceur().getCabinet().getBase().getY()+10
        );
        return new Porte( base,longueur ,hauteur , Color.green ); 
    }
    
     

    
    
    
    

    public int getNbrEtages() {
        return nbrEtages;
    }

    public void setNbrEtages(int nbrEtages) {
        this.nbrEtages = nbrEtages;
    }
    
    

    public Mur getMur() {
        return mur;
    }

    public void setMur(Mur mur) {
        this.mur = mur;
    }

    public Ascenceur getAscenceur() {
        return ascenceur;
    }

    public void setAscenceur(Ascenceur ascenceur) {
        this.ascenceur = ascenceur;
    }

    public Color getColeur() {
        return coleur;
    }

    public void setColeur(Color coleur) {
        this.coleur = coleur;
    }
    

    public Rdc getRdc() {
        return rdc;
    }

    public void setRdc(Rdc rdc) {
        this.rdc = rdc;
    }

    public Toi getToi() {
        return toi;
    }

    public void setToi(Toi toi) {
        this.toi = toi;
    }
 

    public ArrayList<Etage> getEtages() {
        return etages;
    }

    public void setEtages(ArrayList<Etage> etages) {
        this.etages = etages;
    }

    @Override public void dessiner(java.awt.Graphics2D g)
    {    
        //g.setColor( getColeur() );
        //g.fillRect(0, 0, getLongueur(), getHauteur());
        /************************************************/
                
        getRdc().dessiner(g);

        getMur().dessiner(g);
                 
        
        for(Etage e : getEtages())
            e.dessiner(g);
        
        getToi().dessiner(g);
        
        getAscenceur().dessiner(g);
    }
    
     
     

}