package System;

import boule.Game;
import game.Fenetre;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import outils.Image;
import outils.Point; 
import serpent.Souris;






public class PanneauBatiment extends JPanel
{
    private ArrayList<Personne> personnes; 
    private ArrayList<Souris> souris; 
    private Batiment batiment;
    private Canal canal;
    private Image bg;
     
    
    
    
    public PanneauBatiment( int width , int height, Batiment batiment) 
    {          
        this.setBounds(10,357, width+2, height);
        this.setBatiment(batiment); 
        this.setPersonnes( new ArrayList() );
        this.setPersonnes( new ArrayList() );
        this.setSouris(new ArrayList());
        creerPersonnes();  
        creerSouris(); 
        creerCanal();
        creerBg();
        
        draw();
        initListener();   
    }
    
    
    
    public void demarerJeu()
    { 
        java.util.Random rand = new java.util.Random(); 
        for(Personne p : getPersonnes())
        {
            Mission m = new Mission();
            m.setPersonne(p);
            Etage etg;
            do
            {
                etg = getBatiment().getEtages().get(rand.nextInt(getBatiment().getNbrEtages()+1));
            }
            while( p.getEtage().getNum() ==  etg.getNum());
            
            m.setEtageAdeplacer(etg);
            p.allerA( m.getEtageAdeplacer() );
        }          
    }
    
    private void creerPersonnes()
    {
        int taille = batiment.getRdc().getPorte().getHauteur();
        taille = Fenetre.xToPercent(100, taille);
        
        for(int i=0;i<=1;i++)
        {
            Personne p = new Personne(taille,Color.BLUE,getBatiment());
            p.setPosition(new Point(100+i*10*10,batiment.getRdc().getBase().getY()-taille));
            p.setEtage(batiment.getRdc());
            p.setId(i+1);
            getPersonnes().add( p );
        } 
        
        // dans le cabinet
        Personne p = new Personne(taille,Color.BLUE,getBatiment());
        p.setPosition(
            new Point(
                getBatiment().getAscenceur().getCabinet().getPorte().getBase().getX() + getBatiment().getAscenceur().getCabinet().getPorte().getLongueur()/2-p.getTete().getRayon()/2  ,
                batiment.getRdc().getBase().getY()-taille
            )
        );
        p.setEtage( batiment.getEtages().get(0) );
        getPersonnes().add( p );
        batiment.getAscenceur().getCabinet().setPersonne(p);
        /*
        // etage 1 
        Personne p2 = new Personne(taille,Color.RED,getBatiment());
        p2.setPosition(
            new Point(
                getBatiment().getEtages().get(1).getPorte().getBase().getX()-p2.getMain().getLongueur() -p2.getMain().getLongueur()/2+5  ,
                getBatiment().getEtages().get(1).getBase().getY()-taille
            )
        );
        p2.setEtage( batiment.getEtages().get(0) );
        p2.setId(10);
        getPersonnes().add( p2 );*/
    }

    private void creerSouris(){
        Point pos = new Point( 
            15, 
            getBatiment().getRdc().getBase().getY()-40
        );
        Souris s = new Souris(pos,30,Color.black);
        this.getSouris().add(s);
    } 

    private void creerCanal()
    {
        this.setCanal(null);
        this.setCanal(
            new Canal(
                new Point(0,0),
                batiment.getMur().getHauteur()-batiment.getRdc().getHauteur(),
                Color.BLUE
            )
        ); 
    }
     
    private void creerBg()
    {
        String chemin = "C:\\Users\\ABDO\\Documents\\NetBeansProjects\\Jeu3\\src\\res\\bg.jpg";
        bg = new Image(chemin);
    }
    
    // getters / setters
    public ArrayList<Souris> getSouris() 
    {
        return souris;
    }

    public void setSouris(ArrayList<Souris> souris) 
    {
        this.souris = souris;
    }

    public Canal getCanal() 
    {
        return canal;
    }

    public void setCanal(Canal canal) 
    {
        this.canal = canal;
    }
 
    public Batiment getBatiment() 
    {
        return batiment;
    }

    public void setBatiment(Batiment batiment) 
    {
        this.batiment = batiment;
    }

    public ArrayList<Personne> getPersonnes() 
    {
        return personnes;
    }

    public void setPersonnes(ArrayList<Personne> personnes) 
    {
        this.personnes = personnes;
    }
 

    
    
    // paint and graphics
    @Override public void paint(java.awt.Graphics g)
    {    
        g.setColor(Color.white);
        g.drawRect(0, 0, getWidth()-2, getHeight()-2);
        
        g.drawImage(bg.get(), 2, 2, getWidth()-4,getHeight()+100-4, this);
        
        getBatiment().dessiner( (java.awt.Graphics2D)g );
        
        int i=1;
        for(Personne p : getPersonnes()){
            p.dessiner((java.awt.Graphics2D)g);
            g.setColor(Color.BLACK);
            g.drawString(
                ""+i, 
                (int)p.getTete().getCentre().getX()- p.getTete().getRayon()/2, 
                (int)p.getTete().getCentre().getY()- p.getTete().getRayon()/2 
            );
            i++;
        }
        
        getCanal().dessiner((java.awt.Graphics2D)g);
        
        for(Souris s : getSouris()){
            ((java.awt.Graphics2D)g).rotate(-1.58f, 
                    s.getTete().getCentre().getX(), 
                    s.getTete().getCentre().getY()
            );
            s.dessiner((java.awt.Graphics2D)g);
        } 
    }
    
    private void draw()
    {
        new Thread(new Runnable(){ @Override public void run()
        { 
            while(true)
            {
                repaint();
                try{ Thread.sleep(10); }catch(Exception e){}
            }
        }}).start();
    }

    
    // init key listner mouse
    private void initListener()
    { 
        ListnerPersonne lp = new ListnerPersonne();
        lp.setPersonnes(getPersonnes()); 
        
        ListnerEtage le = new ListnerEtage();
        le.setBatiment(getBatiment()); 
        
        ListnerPorte lprt = new ListnerPorte();
        lprt.setBatiment(getBatiment()); 
        
        this.addMouseListener(lp); 
        this.addMouseListener(le);
        this.addMouseListener(lprt); 
        
        
        MouseAdapter adapter = new 
        MouseAdapter()
        {  
            private Color last = getPersonnes().get(0).getColeur();
            private Color lastPort = getBatiment().getEtages().get(0).getPorte().getColeur();
            
            @Override public void mouseMoved(MouseEvent event) 
            {
                java.awt.Point mousePosition = new java.awt.Point(event.getX(),event.getY());
                for(Personne p : getPersonnes())
                {
                    Rectangle rec = new Rectangle(  
                        (int)p.getTete().getCentre().getX()-p.getTete().getRayon()/2,
                        (int)p.getTete().getCentre().getY(),
                        p.getMain().getLongueur(),
                        p.getCorps().getHauteur()
                    );
                    if(rec.contains(mousePosition))
                    {
                        p.setColeur(Color.white);
                        return;
                    }else p.setColeur(last);
                }
                
                for(Etage e : getBatiment().getEtages())
                {
                    Rectangle rec = new Rectangle(  
                        (int)e.getPorte().getBase().getX(),
                        (int)e.getPorte().getBase().getY(),
                        e.getPorte().getLongueur(),
                        e.getPorte().getHauteur()
                    );
                    if(rec.contains(mousePosition))
                    {  
                        e.getPorte().setColeur(Color.yellow);
                    }
                    else e.getPorte().setColeur(lastPort);
                }
                
                Rectangle rec = new Rectangle(  
                    (int)getBatiment().getRdc().getPorte().getBase().getX(),
                    (int)getBatiment().getRdc().getPorte().getBase().getY(),
                    getBatiment().getRdc().getPorte().getLongueur(),
                    getBatiment().getRdc().getPorte().getHauteur()
                );
                if(rec.contains(mousePosition))
                {  
                    getBatiment().getRdc().getPorte().setColeur(Color.yellow);
                }
                else getBatiment().getRdc().getPorte().setColeur(lastPort);
                
                
            }  
            @Override public void mousePressed(MouseEvent event){}  
            @Override public void mouseDragged(MouseEvent event) {}   
        };
        this.addMouseMotionListener(adapter);
    }
    
    
}