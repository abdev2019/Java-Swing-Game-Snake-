package serpent;
  
import java.util.Date;
import java.util.Timer;
import outils.Point;
import outils.Deplacement;



public class Serpent 
{     
    private Tete tete; 
    private int nbrPoints;
    private java.awt.Color coleur;
    private java.util.ArrayList<Anneau> anneaux; 
     
    private Deplacement  dep;  
    java.util.Random rand = new java.util.Random(); 
    int tab[] = { -20,20 };
    int indice = 0;
    
    
    Thread timer;
     
    
    public Serpent(Tete t, int n, java.awt.Color coleur)
    {
        setNbrPoints(n);  
        setTete(t);  
        setColeur(coleur);
        setDeplacement( new Deplacement(1, new Point(0,t.getPosition().getY())) ); 
        initAnneaux(); 
        Runnable r = new Runnable(){
            @Override
            public void run() {
                while(true)
                { 
                    if(indice == 1)
                        indice = 0;
                    else indice = 1;
                    try{ Thread.sleep(1000); }catch(Exception e){};
                }
            } 
        }; 
        timer = new Thread(r);
        timer.start();
    }
    
    
    
    public void initAnneaux()
    {
        java.util.ArrayList<Anneau> ans = new java.util.ArrayList();
        int ray = 40;
        float x = getTete().getPosition().getX();
        float y = getTete().getPosition().getY(); 
        for(int i=1; i<=getNbrPoints();i++)
        {
            Anneau a = new Anneau( new Point(x+(ray-i)*i,getTete().getPosition().getY()), ray-i, getColeur() ); 
            a.setNext(getTete().getPosition()); 
            ans.add( a ); 
        }
        setAnneaux(ans); 
    }
    
    
    public void randomMove()
    {  
        getDeplacement().getFin().setY( 
            getDeplacement().getFin().getY()
            +
            tab[ indice ]
        ); 

        getDeplacement().getFin().setX( 
            getDeplacement().getFin().getX()
            +
            tab[ indice==1?0:1 ]
        ); 
        
        //else
        /*{
            
        }*/
    }
    
    public Feu jeterFeu(){ 
        Feu f = new Feu(getTete().getPosition());
        f.getDep().setFin(new Point(getDeplacement().getFin()));
        return f;
    }  
 
    public java.awt.Color getColeur() {
        return coleur;
    }

    public void setColeur(java.awt.Color coleur) {
        this.coleur = coleur;
    }
  
    public void setAnneaux(java.util.ArrayList<Anneau> anneaux) {
        this.anneaux = anneaux;
    }

    public java.util.ArrayList<Anneau> getAnneaux() {
        return anneaux;
    }

    public Anneau getAnneau(int i) {
        return getAnneaux().get(i);
    }
    
    public void setTete(Tete tete) {
        this.tete = tete;
    }

    public void setNbrPoints(int nbrPoints) {
        this.nbrPoints = nbrPoints;
    }

    public Tete getTete() {
        return tete;
    }

    public int getNbrPoints() {
        return nbrPoints;
    } 
    
    public Deplacement getDeplacement() {
        return dep;
    }

    public void setDeplacement(Deplacement dep) {
        this.dep = dep;
    } 
    
    
    // graphic
    public void dessiner(java.awt.Graphics2D g)
    {
        getTete().dessiner(g); 
        g.setColor(this.coleur);
        for( int i=0; i<getNbrPoints(); i++ ) 
            getAnneau(i).dessiner(g); 
    }
}
