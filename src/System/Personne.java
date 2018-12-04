package System;

import game.Fenetre;
import java.awt.BasicStroke;
import java.awt.Color;
import java.util.Timer;
import outils.Cercle;
import outils.Deplacement;
import outils.Ligne;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:42
 */
public class Personne implements IDeplacement, IDessin 
{
    private boolean arrive;
    private Color coleur;
    private Rect main;
    private Rect corps;
    private Cercle tete;
    private Ligne[] pieds = new Ligne[2];
    private int taille;
    
    
    private Point position;
    private Etage etage = null;
    private int id;
    
    private Batiment batiment;

    
    
    public Personne(int taille, Color c, Batiment batiment){
        this.setTaille(taille);
        this.setArrive(false);
        this.setColeur(c);
        this.setBatiment(batiment); 
        this.setPosition(new Point(100,batiment.getRdc().getBase().getY()-taille));
    } 
    
    
    private void init(){ 
        int hTete = Fenetre.xToPercent(30, getTaille());
        
        getPosition().setY(getPosition().getY() + hTete);
        Cercle c   = new Cercle(getPosition(),hTete);
        //c.getCentre().setY( c.getCentre().getY() );
         
        Point b1   = new Point( getPosition().getX()-hTete/2.0f, getPosition().getY()+hTete );
        Rect main  = new Rect(b1, hTete*2, 5  );
        
        Point b2   = new Point( getPosition().getX() +hTete/2.0f - 3  ,getPosition().getY() );
        Rect corps = new Rect(b2, 6, getTaille()-hTete  );
        
        pieds[0] = new Ligne( 
            new Point(
                corps.getBase().getX()+5,
                corps.getBase().getY()+getTaille()-getTaille()/2
            ) , 
            new Point(
                corps.getBase().getX()-20,
                corps.getBase().getY()+getTaille()
            ) 
        );
        pieds[1] = new Ligne( 
            new Point(
                corps.getBase().getX()+5,
                corps.getBase().getY()+getTaille()-getTaille()/2
            ) , 
            new Point(
                corps.getBase().getX()+20,
                corps.getBase().getY()+getTaille()
            ) 
        );
        
        
        this.setTete(c);
        this.setCorps(corps);
        this.setMain(main);
    }
     
    

    public void allerA(Etage e)
    { 
        // allez vers la porte 
        float x = batiment.getAscenceur().getBase().getX()-getEtage().getPorte().getLongueur()-getMain().getLongueur();
        float y = getTete().getCentre().getY();
        Point next = new Point(x,y) ; 
 
        new Thread(new Runnable(){ @Override public void run()
        {     
            while(true)
            {
                mouver(next);
                if(getTete().getCentre().identique(next,1))  
                    break; 
                try{ Thread.sleep(17); }catch(Exception e){} 
            }
            getBatiment().getAscenceur().getCabinet().getMissions().add( new Mission(getThis(),e) );
        }}).start();  
    } 
    
    private Personne getThis(){ return this; }
    
    
    
    public void sauter(Point pp)
    {
        new Thread( new Runnable(){ @Override public void run()
        {
            Point p = new Point(pp);
            p.setY( p.getY()-getCorps().getHauteur() );
            while(true)
            {
                float deltaX = p.getX() - getTete().getCentre().getX();
                float deltaY = p.getY() - getTete().getCentre().getY();
                double angle = Math.atan2( deltaY, deltaX );
                mouver( new Point( getTete().getCentre().getX() + (float)(3 * Math.cos( angle )),
                    getTete().getCentre().getY() + (float)(1 * Math.sin( angle ))
                ) ); 
                if( getTete().getCentre().identique(p, 1) ){
                    setEtage(getBatiment().getRdc());
                    java.util.Random rand = new java.util.Random(); 
                    getBatiment().getAscenceur().getCabinet().getMissions().add(
                        new Mission(
                            getThis(), 
                            getBatiment().getEtages().get( rand.nextInt(getBatiment().getNbrEtages()) )
                        )
                    );
                    return;
                }
                try{ Thread.sleep(17); }catch(Exception e){} 
            }
        }}).start();
    }
    
    
    
    public void mouver(Point next)
    {   
        Deplacement dep = new Deplacement(2,next);
        
        int hTete = Fenetre.xToPercent(30, getTaille()); 

        Point bm   = new Point( next.getX()-hTete/2.0f, next.getY()+hTete );

        Point bc   = new Point( next.getX() +hTete/2.0f - 3  ,next.getY() );

        getTete().mover(dep, next);
        getMain().getBase().mouver(dep, bm); 
        getCorps().getBase().mouver(dep, bc); 
    }

    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    
    
    public Etage getEtage() {
        return etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }
    
    

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
        init();
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

    public Rect getMain() {
        return main;
    }

    public void setMain(Rect main) {
        this.main = main;
    }

    public Rect getCorps() {
        return corps;
    }

    public void setCorps(Rect corps) {
        this.corps = corps;
    }

    public Color getColeur(){
            return coleur;
    }

    public boolean isArrive(){
            return arrive;
    }



    public void setArrive(boolean newVal){
            arrive = newVal;
    }

    public void setColeur(Color newVal){
            coleur = newVal;
    }


    @Override public void dessiner(java.awt.Graphics2D g){  
        g.setColor( getColeur() );
        g.fillOval(
            (int)getTete().getCentre().getX(), 
            (int)getTete().getCentre().getY(), 
            getTete().getRayon(), 
            getTete().getRayon()
        );
        g.fillRect(
            (int)getMain().getBase().getX(), 
            (int)getMain().getBase().getY(), 
            getMain().getLongueur(), 
            getMain().getHauteur()
        );
        g.fillRect(
            (int)getCorps().getBase().getX(), 
            (int)getCorps().getBase().getY(), 
            getCorps().getLongueur(), 
            getCorps().getHauteur()
        ); 
        //g.setStroke(new BasicStroke(5));
        /*for(Ligne pied : pieds)
            g.drawLine(
                (int)pied.getP1().getX(), 
                (int)pied.getP1().getY(), 
                (int)pied.getP2().getX(), 
                (int)pied.getP2().getY()
            );*/
    }
}