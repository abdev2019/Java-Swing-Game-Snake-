package boule;

import System.Mur; 
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import outils.Deplacement;
import outils.Point;
import serpent.Anneau;



public class Boule extends Anneau 
{ 
    Deplacement dep; 
    boolean explose     = false; 
    boolean enExplosant = false;  
    ArrayList<Boule> effectsExplosion = new ArrayList();
    
    boolean playing = false;
    
    Thread thread;
    
    
    public Boule( Point c, int r,  java.awt.Color color, int vitesse)
    {
        super(c,r,color);
        // la balle est en stop avec vitesse 1
        this.dep = new Deplacement(vitesse, null);
    }
    
    public void jeter(Mur floor)
    {
        // point à envoyer la balle c'est le floor
        dep.setFin( new Point(this.getCentre().getX(), floor.getBase().getY()) );
        
        Runnable r = new Runnable() 
        {
            @Override public void run()  
            {  
                playing = true;
                while(playing)
                {   
                    if( !arriverAuFloor(floor) )
                        getCentre().mouver(dep, dep.getFin());
                    
                    else if( !isEnExplosant() )   
                        exploser();
                    
                    try{ Thread.sleep(20); } catch (Exception e) {} 
                }
            }
        };
                
        thread = new Thread(r);
        thread.start();
    }
    
    public void detruire(){
        playing = false;
        thread = null;
        effectsExplosion = null;
    }
    
    boolean arriverAuFloor(Mur floor)
    {
        return  getCentre().getY()+getRayon()
                >= 
                floor.getBase().getY()-floor.getHauteur() ;
    }

    public boolean isExplose() 
    {
        return explose;
    }

    public void setExplose(boolean explose) 
    {
        this.explose = explose;
    } 
    
    public void exploser()
    {
        enExplosant = true;
        java.util.Random rand = new java.util.Random();
        int x = -2000;
        for(int i=0;i<20;i++)
        {
            Boule b = new Boule( new Point(this.getCentre()),this.getRayon(), this.getColor(), 10 );
            b.dep.setFin( 
                new Point( 
                    x, this.getCentre().getY() - 800
                ) 
            );
            effectsExplosion.add(b);
            x+=500;
        }
        this.setRayon(0);
        startEffetExplosion();
    }
     
    public boolean isEnExplosant() 
    {
        return enExplosant;
    }
     
    void startEffetExplosion() 
    { 
        Runnable r = new Runnable() 
        {
            @Override public void run()  
            {  
                while(true) 
                {   
                    for(int i=0; i<effectsExplosion.size();i++)
                    { 
                        effectsExplosion.get(i).mover(
                            effectsExplosion.get(i).dep, 
                            effectsExplosion.get(i).dep.getFin()
                        );
                         
                        
                        effectsExplosion.get(i).setRayon(
                            effectsExplosion.get(i).getRayon() - 5
                        );
                        
                        
                        if(effectsExplosion.get(i).getRayon()<=0)
                            effectsExplosion.remove(i);
                    } 
                    
                    if( effectsExplosion.isEmpty() )
                        break; 
                    
                    try{ Thread.sleep(30); } catch (Exception e) {}
                } 
                    
                explose = true;
            } 
        }; 
        new Thread(r).start();
    }
  
    public void dessiner(Graphics g) 
    {    
        synchronized(this)
        {
            try
            {
                super.dessiner((Graphics2D)g);
                if(isEnExplosant())
                {
                    for(int i=0;i<effectsExplosion.size();i++) 
                    {
                        Boule bb = effectsExplosion.get(i); 
                        if(bb!=null)
                            bb.dessiner((Graphics2D)g);  
                    }
                } 
            }catch(Exception ex){}
        }
    }
     
}
