package System;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Timer;
import outils.Deplacement;
import outils.Point;
import outils.Rect;


/**
 * @author ABDO
 * @version 1.0
 * @created 21-oct.-2017 15:16:41
 */
public class Cabinet extends Rect implements IDessin
{
    private Color coleur;
    private Etage etage;
    private Porte porte;
    private Batiment batiment;
    private Personne personne;
    
    
    private Thread tSynchronization; 
    private ArrayList<Mission> missions;  
    
    

    public Cabinet(int hauteur, int longueur, Point base, Color coleur) {
        super(base, hauteur, longueur);
        this.coleur = coleur; 
        setEtage(null);
        this.setMissions( new ArrayList() );
        synchronizer();
    }
    
 
    public void ouvrirPorte()
    { 
        if( !getPorte().isOuvert() )
        {
            new Thread(new Runnable(){ @Override public void run()
            { 
                while(true)
                { 
                    getPorte().setHauteur( getPorte().getHauteur()-1 );
                    if(getPorte().getHauteur() <=1 )
                    {
                        getPorte().setOuvert(true); 
                        break;
                    }
                    try{ Thread.sleep(17); }catch(Exception e){}
                }
            }}).start();
        }
        else fermerPorte();
    }
    public void fermerPorte(){ 
        if( getPorte().isOuvert() )
        {
            new Thread(new Runnable(){ @Override public void run()
            { 
                while(true)
                {
                    getPorte().setHauteur( getPorte().getHauteur()+1 );
                    if(getPorte().getHauteur() >=getHauteur()-10 )
                    {
                        getPorte().setOuvert(false); 
                        return;
                    }
                    try{ Thread.sleep(17); }catch(Exception e){}
                }
            }}).start();
        } 
    }
    
 
    public void deplacer(Etage e)
    {
        setEtage(null);
        new Thread(new Runnable(){ @Override public void run()
        {  
            Point pCabinet =   new Point(
                            getBase().getX(),
                            e.getBase().getY()-getHauteur()
                        );
            Point pPortCabinet =   new Point(
                            getPorte().getBase().getX(),
                            e.getBase().getY()-getHauteur()+10
                        );
            
            Deplacement dep = new Deplacement( 2,null );
            
            
            while(true)
            {
                getBase().mouver(dep, pCabinet); 
                
                getPorte().getBase().mouver(dep, pPortCabinet);
                
                if(getPersonne() != null)
                {
                    Point pPersonneCabinet =   new Point(
                            getPersonne().getTete().getCentre().getX(),
                            e.getBase().getY()-getHauteur()
                        );
                    pPersonneCabinet.setX(pPersonneCabinet.getX());
                    pPersonneCabinet.setY(pPersonneCabinet.getY() + getPersonne().getTete().getRayon());
                    getPersonne().mouver(pPersonneCabinet);
                }
                
                if(getBase().identique(pCabinet,2))  {
                    setEtage( e );
                    if(getPersonne() != null){
                        getPersonne().setEtage(e);
                        setPersonne(null);
                    } 
                    while(!getPorte().getBase().identique(pPortCabinet,2))
                        getPorte().getBase().mouver(dep, pPortCabinet);
                        
                    return;
                }
                
                try{ Thread.sleep(17); }catch(Exception exp){}
            }
        }}).start();
    }

    
    
    
    public void synchronizer()
    {
        tSynchronization = new Thread
        (
            new Runnable()
            {
                @Override public void run()
                {
                    while(true)
                    {                         
                        while( getMissions().size() > 0 )
                        {
                            Mission m  = getMissions().remove(0);
                            Personne p = m.getPersonne();
                            Etage e    = m.getEtageAdeplacer(); 


                            // appel le cabinet
                            if(getEtage().getNum() != p.getEtage().getNum())
                            {
                                deplacer( p.getEtage() ); 

                                while(true)
                                {
                                    if( getEtage() != null ) break;  
                                    try{ Thread.sleep(17); }catch(Exception exp){} 
                                } 
                            } 
 
                            // ouvrir porte etage
                            getEtage().getPorte().ouvrir(); 
                            ouvrirPorte(); 
                            
                            while( !getEtage().getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 

                            //ouvrir porte cabinet
                            while( !getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 


                            //Entrez dans la cabinet  
                            Point versCabinet = new Point(
                                getPorte().getBase().getX() + getPorte().getLongueur()/2-p.getTete().getRayon()/2  ,
                                p.getTete().getCentre().getY()
                            );

                            while(true)
                            {
                                p.mouver(versCabinet);
                                if(p.getTete().getCentre().identique(versCabinet,1)) break;  
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            } 
                            setPersonne(p);


                            // fermer les portes
                            fermerPorte();
                            getEtage().getPorte().fermer();  
                            
                            while( getEtage().getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 

                            //ouvrir porte cabinet
                            while( getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 


                            // allez !!!!
                            deplacer(e); 
                            while(true)
                            {
                                if(getEtage() != null)
                                {
                                    if(getEtage().getNum() == e.getNum())
                                    {  
                                        ouvrirPorte();
                                        e.getPorte().ouvrir();

                                        while( !e.getPorte().isOuvert() ) 
                                            try{ Thread.sleep(17); }catch(Exception exp){} 

                                        break;
                                    }
                                }
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            } 

                            //arrivé  
                            Point next2 = new Point(e.getBase().getX(), p.getTete().getCentre().getY());
                            Runnable r = new Runnable(){
                                @Override public void run(){
                                    fermerPorte();
                                    getEtage().getPorte().fermer(); 
                                }
                            };

                            Timer t = new Timer();
                            t.schedule(
                                new java.util.TimerTask() {          
                                    @Override public void run() { 
                                        fermerPorte();
                                        e.getPorte().fermer(); 
                                    }
                            }, 1500);
                            
                             
                            while( !getEtage().getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 

                            //ouvrir porte cabinet
                            while( !getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 

                            while(true)
                            {
                                p.mouver(next2);
                                if(p.getTete().getCentre().identique(next2,1))  
                                    break; 
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            }
                            
                            t.cancel();
                            
                            p.sauter( new Point( 100,getBatiment().getRdc().getBase().getY() ) );
                            
                            try{ Thread.sleep(50); }catch(Exception exp){} 
                        } 
                        try{ Thread.sleep(50); }catch(Exception e){} 
                    }
                }
            }
        );
        tSynchronization.start();
    }
    
    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Personne getPersonne() {
        return personne;
    }

    public void setPersonne(Personne personne) {
        this.personne = personne;
    }
    
    
    
    
    
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

        

    public ArrayList<Mission> getMissions() {
        return missions;
    }

    public void setMissions(ArrayList<Mission> missions) {
        this.missions = missions;
    }
      
    

    public Etage getEtage() {
        return etage;
    }

    public void setEtage(Etage etage) {
        this.etage = etage;
    }
    
    
    
    @Override public void dessiner(java.awt.Graphics2D g){ 
        g.setColor( getColeur() );
        g.fillRect((int)getBase().getX(), (int)getBase().getY(), getLongueur(), getHauteur());
        
        getPorte().dessiner(g);
    }


}















































/*


                            //allez proche d'asceseur;
                            float x = batiment.getAscenceur().getBase().getX()-p.getEtage().getPorte().getLongueur()-p.getMain().getLongueur();
                            float y = p.getPosition().getY();
                            Point next = new Point(x,y) ; 
 
                            while(true)
                            {
                                p.mouver(next);
                                if(p.getTete().getCentre().identique(next,1))  
                                    break; 
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            } 
    
    public void synchronizer()
    {
        tSynchronization = new Thread
        (
            new Runnable()
            {
                @Override public void run()
                {
                    while(true)
                    {
                        while( getMissions().size() > 0 )
                        {
                            Mission m  = getMissions().remove(0);
                            Personne p = m.getPersonne();
                            Etage e    = m.getEtageAdeplacer();
                            
                            // appel le cabinet  
                            while(true)
                            { 
                                if( getBatiment().getAscenceur().getCabinet().getEtage() != null ){ 
                                    if( getBatiment().getAscenceur().getCabinet().getPersonnes().size() == 0  )
                                        break;
                                }

                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            }


                            if(getBatiment().getAscenceur().getCabinet().getEtage().getNum() != getEtage().getNum())
                            {
                                getBatiment().getAscenceur().getCabinet().deplacer( getEtage() ); 

                                while(true){
                                    if( getBatiment().getAscenceur().getCabinet().getEtage() != null )
                                        if(getBatiment().getAscenceur().getCabinet().getEtage().getNum() == getEtage().getNum())
                                            break;  
                                    try{ Thread.sleep(17); }catch(Exception exp){} 
                                } 
                            }
                            getBatiment().getAscenceur().getCabinet().setEtage(null);



                            // ouvrir porte etage
                            getEtage().getPorte().ouvrir();
                            while( !getEtage().getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 

                            //ouvrir porte cabinet
                            getBatiment().getAscenceur().getCabinet().ouvrirPorte(); 
                            while( ! getBatiment().getAscenceur().getCabinet().getPorte().isOuvert() )
                                try{ Thread.sleep(17); }catch(Exception exp){} 


                            //Entrez dans la cabinet  
                            Point versCabinet = new Point(
                                getPorte().getBase().getX() + getPorte().getLongueur()/2-p.getTete().getRayon()/2  ,
                                p.getTete().getCentre().getY()
                            );

                            while(true)
                            {
                                p.mouver(versCabinet);
                                if(p.getTete().getCentre().identique(versCabinet,1)) break;  
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            } 


                            // fermer les portes
                            getBatiment().getAscenceur().getCabinet().fermerPorte();
                            getEtage().getPorte().fermer(); 



                            // allez !!!!
                            getBatiment().getAscenceur().getCabinet().deplacer(e);
                            while(true)
                            {
                                if(getBatiment().getAscenceur().getCabinet().getEtage() != null)
                                {
                                    if(getBatiment().getAscenceur().getCabinet().getEtage().getNum() == e.getNum())
                                    {  
                                        getBatiment().getAscenceur().getCabinet().ouvrirPorte();
                                        e.getPorte().ouvrir();

                                        while( !e.getPorte().isOuvert() ) 
                                            try{ Thread.sleep(17); }catch(Exception exp){} 

                                        break;
                                    }
                                }
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            } 

                            //arrivé  
                            Point next = new Point(e.getBase().getX(), p.getTete().getCentre().getY());
                            Runnable r = new Runnable(){
                                @Override public void run(){
                                    getBatiment().getAscenceur().getCabinet().fermerPorte();
                                    getEtage().getPorte().fermer(); 
                                }
                            };

                            Timer t = new Timer();
                            t.schedule(
                                new java.util.TimerTask() {          
                                    @Override public void run() { 
                                        getBatiment().getAscenceur().getCabinet().fermerPorte();
                                        e.getPorte().fermer(); 
                                    }
                            }, 1000);

                            while(true)
                            {
                                p.mouver(next);
                                if(p.getTete().getCentre().identique(next,1))  
                                    break; 
                                try{ Thread.sleep(17); }catch(Exception exp){} 
                            }
                            getBatiment().getAscenceur().getCabinet().getPersonnes().remove(0);
                            t.cancel();
                        }
                        try{ Thread.sleep(50); }catch(Exception e){} 
                    }
                }
            }
        );
        tSynchronization.start();
    }
*/