
package serpent;

 
import System.Mur;
import boule.Boule;
import boule.Game;
import outils.Point; 
import java.awt.Color; 
import java.awt.Graphics;
import java.awt.Graphics2D; 
import java.awt.Rectangle;  
import java.util.ArrayList;
import java.util.Random; 
import javax.swing.JPanel; 



public class PanneauSerpent extends JPanel 
{
    // Creation de serpent, zoo, goal et zoo 
    private Serpent       serpent; 
    public ArrayList<Serpent> serpents = new ArrayList();
    private int           score; 
    private Goal          goal;  
    Zoo zoo; 
    private ArrayList<Feu> feux;
    private ArrayList<Souris> souris;
    
    private ArrayList<Boule> balles = new ArrayList();
    public Mur floor;
    
    private ArrayList<Anneau> canal;
    
    // configuration d'environnement 
    private Thread  thread=null;
    private boolean playing;
    private boolean stop; 
     
    
    
    
    
    public PanneauSerpent(Serpent serpent, Zoo z)
    { 
        setSerpent(serpent); 
        setZoo(z);    
        setGoal( new Goal(new Point(100,300),50,50, Color.red) ); 
        setSouris(new ArrayList());
        creerSouris(); 
        setCanal(new ArrayList()); 
        setStop(true);
        setFeux(new ArrayList());
        Feu.chargerImage(); 
        floor = new Mur( new Point(0,z.getSurface().getHauteur()-20) , z.getSurface().getLongueur(), 20, Color.darkGray );
        start();  
        
        this.setBounds(10, 5, z.getSurface().getLongueur()+1, z.getSurface().getHauteur()+1);
    }
    
     
    
    Color[] clrs = {Color.BLACK, Color.DARK_GRAY, Color.GRAY};/*
    Color[] clrs = {Color.BLACK, Color.BLUE,Color.CYAN, Color.DARK_GRAY, Color.GRAY, Color.LIGHT_GRAY,
    Color.MAGENTA, Color.ORANGE, Color.RED, Color.WHITE, Color.YELLOW};*/
    java.util.Random rand = new java.util.Random();
    public void ajouterSerpent()
    {
        String chemin = "/res/tete.png";
        Tete  tete = new Tete(new Point(400,110),chemin);           
        Serpent serp = new Serpent(tete,10, clrs[ rand.nextInt(3) ]);   
        serpents.add(serp); 
    }

    private void creerSouris(){
        Point pos = new Point(getWidth()/4,getHeight()-30);
        Souris s = new Souris(pos,30,Color.LIGHT_GRAY);
        this.getSouris().add(s);
        
        pos = new Point(getWidth() - 30,getHeight()-30);
        s = new Souris(pos,30,Color.LIGHT_GRAY);
        this.getSouris().add(s);
    }
    
    public void creerCanal(Point base, int ray, int nbr)
    { 
        float x = base.getX();
        float y = this.getHeight()-ray; 
        for(int i=1; i<=nbr;i++)
        {
            Anneau a = new Anneau( new Point(x+(ray-10)*i,y), ray, Color.cyan ); 
            canal.add( a ); 
        }
    } 
    
    public ArrayList<Boule> getBalles(){
        return balles;
    }
     
    
    
  
    
    public void start()
    {
        playing = true; 
        thread  = new Thread( new Runnable() { 
            @Override public void run() 
            {          
                boolean tested = false;
                java.util.Random rand = new java.util.Random(); 

                int Low,High,ResultY,ResultX;
                
                                
                while (playing) 
                {      
                    
                    repaint();
        
                    if(!stop)
                    {  
                       /* if( testCollisionGoal(getSerpent().getTete().getPosition()) && !tested ){ 
                            getSerpent().getDeplacement().setVitesse(1);
                            getSerpent().getDeplacement().setFin( 
                                new Point(
                                    getGoal().getBase().getX()+6,
                                    getGoal().getBase().getY()+6
                                ) 
                            );
                            //randomGoal();
                            tested = true; 
                        }*/
                        
                        if(!tested)
                        { 
                            getSerpent().randomMove();

                            /*for(Souris s : getSouris())
                                s.randomMove();*/


                            /*if(tested && !testCollisionGoal(getSerpent().getTete().getPosition())) 
                                tested=false;*/ 

                            for(int i=0; i<serpents.size(); i++)
                                if( 
                                    testCollisionMur(i) 
                                )
                                { 
                                    Point pnt[] = new Point[4];
                                    Low  = 10;
                                    High = 100;
                                    ResultX = rand.nextInt(High-Low) + Low;
                                    if( ResultX > 100 ) 
                                        ResultX=100;
                                    else if( ResultX < 0 ) 
                                        ResultX=10;
                                    pnt[0] = new Point(ResultX, 0);


                                    Low  = getWidth()-100;
                                    High = getWidth();
                                    ResultX = rand.nextInt(High-Low) + Low;
                                    if( ResultX > getWidth() ) 
                                        ResultX=getWidth();
                                    else if( ResultX < getWidth()-100 ) 
                                        ResultX=getWidth()-100;
                                    pnt[1] = new Point(ResultX, 0);

                                    Low  = 10;
                                    High = 100;
                                    ResultY = rand.nextInt(High-Low) + Low;
                                    if( ResultY > 100 ) 
                                        ResultY=100;
                                    else if( ResultY < 10 ) 
                                        ResultY=10;
                                    pnt[2] = new Point(0, ResultY);

                                    Low  = getHeight()-100;
                                    High =  getHeight();
                                    ResultY = rand.nextInt(High-Low) + Low;
                                    if( ResultY >  getHeight() ) 
                                        ResultY= getHeight();
                                    else if( ResultY < getHeight()-100 ) 
                                        ResultY= getHeight()-100;
                                    pnt[3] = new Point(0, ResultY);


                                    Point p = pnt[ rand.nextInt(4) ]; 
                                    serpents.get(i).getDeplacement().setFin( 
                                        new Point( p ) 
                                    ); 
                                } 
                            
                            if( 
                                testCollisionMur() 
                            )
                            { 
                                Point pnt[] = new Point[4];
                                Low  = 10;
                                High = 100;
                                ResultX = rand.nextInt(High-Low) + Low;
                                if( ResultX > 100 ) 
                                    ResultX=100;
                                else if( ResultX < 0 ) 
                                    ResultX=10;
                                pnt[0] = new Point(ResultX, 0);


                                Low  = getWidth()-100;
                                High = getWidth();
                                ResultX = rand.nextInt(High-Low) + Low;
                                if( ResultX > getWidth() ) 
                                    ResultX=getWidth();
                                else if( ResultX < getWidth()-100 ) 
                                    ResultX=getWidth()-100;
                                pnt[1] = new Point(ResultX, 0);

                                Low  = 10;
                                High = 100;
                                ResultY = rand.nextInt(High-Low) + Low;
                                if( ResultY > 100 ) 
                                    ResultY=100;
                                else if( ResultY < 10 ) 
                                    ResultY=10;
                                pnt[2] = new Point(0, ResultY);

                                Low  = getHeight()-100;
                                High =  getHeight();
                                ResultY = rand.nextInt(High-Low) + Low;
                                if( ResultY >  getHeight() ) 
                                    ResultY= getHeight();
                                else if( ResultY < getHeight()-100 ) 
                                    ResultY= getHeight()-100;
                                pnt[3] = new Point(0, ResultY);


                                Point p = pnt[ rand.nextInt(4) ]; 
                                getSerpent().getDeplacement().setFin( 
                                    new Point( p ) 
                                );

                                /*javax.swing.JOptionPane.showConfirmDialog (null, "Perdu !", "Confiramtion", javax.swing.JOptionPane.YES_NO_OPTION ); 
                                getSerpent().getTete().setPosition(new Point(100,100));
                                getSerpent().initAnneaux();
                                stop = true;*/
                            }  
                        }
                        
                        update(); 
                    } 
                    
                    try{ Thread.sleep(20); }catch (Exception e){} 
                }
            }
        } ); 
        thread.start();  
    } 
      
    // updating & drawing
    private void update()
    {        
        //for(Souris s : getSouris())
          //  s.mouver();
        
        for(int i=0; i<getBalles().size(); i++)
        {  
            boolean test = false;
            for(int j=0; j<serpents.size();j++) 
            {  
                try
                {
                    if( testCollisionSerpentBoule( serpents.get(j), getBalles().get(i) ) )
                    {
                        serpents.remove(j);
                        getBalles().get(i).exploser(); 
                        test = true;
                        break;
                    } 
                }catch(Exception ex){}
            } 
             
            if( !test && getBalles().get(i).isExplose() ) 
            {
                getBalles().get(i).detruire();
                getBalles().remove(i); 
            } 
        }
        
        // mouvement
        for(int i=0; i<serpents.size(); i++)
        { 
            serpents.get(i).randomMove();
            
            serpents.get(i).getTete().getPosition().mouver
            ( 
                serpents.get(i).getDeplacement() , 
                serpents.get(i).getDeplacement().getFin()
            );   
            
            for(int j=0; j<serpents.get(i).getNbrPoints(); j++)
            {   
                serpents.get(i).getAnneaux().get(j).mover 
                ( 
                    serpents.get(i).getDeplacement(),
                    serpents.get(i).getAnneaux().get(j).getNext()
                ); 
            }
            
            if(
                serpents.get(i).getAnneaux().get(0).getCentre().getDistance(
                    this.serpents.get(i).getTete().getPosition()
                )>= serpents.get(i).getAnneau(0).getRayon()-serpents.get(i).getDeplacement().getVitesse()
            )            
                serpents.get(i).getAnneaux().get(0).setNext(this.serpents.get(i).getTete().getPosition());

            for(int j=1; j<serpents.get(i).getNbrPoints(); j++)
            {    
                if(
                    serpents.get(i).getAnneaux().get(j).getCentre().getDistance(
                        serpents.get(i).getAnneaux().get(j-1).getCentre()
                    )>= serpents.get(i).getAnneaux().get(j-1).getRayon()-5-serpents.get(i).getDeplacement().getVitesse()
                ){
                    serpents.get(i).getAnneaux().get(j).setNext(
                        serpents.get(i).getAnneaux().get(j-1).getCentre()
                    ); 
                }
            }
        }
        
        
        getSerpent().getTete().getPosition().mouver( 
            getSerpent().getDeplacement() , 
            getSerpent().getDeplacement().getFin()
        );   

        for(int i=0; i<getSerpent().getNbrPoints(); i++)
        {   
            getSerpent().getAnneaux().get(i).mover( 
                getSerpent().getDeplacement(),
                getSerpent().getAnneaux().get(i).getNext()
            ); 
        }  
        
        // test
        if(
            getSerpent().getAnneaux().get(0).getCentre().getDistance(
                this.getSerpent().getTete().getPosition()
            )>= getSerpent().getAnneau(0).getRayon()-getSerpent().getDeplacement().getVitesse()
        )            
            getSerpent().getAnneaux().get(0).setNext(this.getSerpent().getTete().getPosition());

        for(int i=1; i<getSerpent().getNbrPoints(); i++)
        {    
            if(
                getSerpent().getAnneaux().get(i).getCentre().getDistance(
                    getSerpent().getAnneaux().get(i-1).getCentre()
                )>= getSerpent().getAnneaux().get(i-1).getRayon()-5-getSerpent().getDeplacement().getVitesse()
            ){
                getSerpent().getAnneaux().get(i).setNext(
                    getSerpent().getAnneaux().get(i-1).getCentre()
                ); 
            }
        }
        /*
          */
            
        
        
        for(int i=0;i< getFeux().size();i++)
        {
            if( getFeu(i)!=null && testCollisionGoal(getFeu(i).getPosition()) ){
                randomGoal(); 
                removeFeu(i); 
            }
            
            else if( getFeu(i)!=null && getFeu(i).isArrive() ){
                removeFeu(i);   
            } 
            //if(if( getFeu(i) == null) getFeux()
        } 
    }
    
    private void draw(Graphics gg)
    { 
        Graphics2D g = (Graphics2D)gg; //this.getGraphics();
          
        // cadre 
        getZoo().dessiner(g);  
          
        // serpent 
        getSerpent().dessiner(g); 
        
        for(int i=0; i<serpents.size(); i++)
        { 
            serpents.get(i).dessiner(g);
        }
        
        //feux
       for(int i=0;i<getFeux().size();i++)
           if( getFeu(i)!=null ) getFeu(i).dessiner(g);
        
        // etoile goal 
        getGoal().dessiner(g);
        
        for(Souris s : getSouris())
        {
            s.dessiner(g);
        }
        
        for(Anneau an : canal)
        {
            an.dessiner(g);
        }
        
        for(int i=0; i<balles.size(); i++)
        {
            synchronized(this){
                if(balles.get(i) != null){ 
                    balles.get(i).dessiner(gg); 
                }
            }
        }   
         
        
        // score **********************************
        g.setColor(Color.WHITE);
        g.drawString("Score : "+getScore(), 10, 20);
           
        g.dispose();
    }
      
    @Override public void paint(Graphics g)
    {
        draw(g);  
    }
    
    
    
    
    // les collisions
    private boolean testCollisionMur(){  
        Rectangle r = new Rectangle(  
            (int)getZoo().getSurface().getBase().getX(),
            (int)getZoo().getSurface().getBase().getY(),
            getZoo().getSurface().getLongueur()-30,
            getZoo().getSurface().getHauteur()-30
        );
        
        return ! r.contains(getSerpent().getTete().getPosition().getX(), getSerpent().getTete().getPosition().getY()); 
    } 
    
    private boolean testCollisionMur(int i){  
        Rectangle r = new Rectangle(  
            (int)getZoo().getSurface().getBase().getX(),
            (int)getZoo().getSurface().getBase().getY(),
            getZoo().getSurface().getLongueur()-30,
            getZoo().getSurface().getHauteur()-30
        );
        
        return 
        ! r.contains(serpents.get(i).getTete().getPosition().getX(), 
                serpents.get(i).getTete().getPosition().getY()
        ); 
    } 
    
    private boolean testCollisionGoal(Point avec){  
        Rectangle r = new Rectangle(  
            (int)getGoal().getBase().getX(),
            (int)getGoal().getBase().getY(),
            getGoal().getLongueur(),
            getGoal().getHauteur()
        );
        
        Rectangle in = new Rectangle(  
            (int)avec.getX(),
            (int)avec.getY(),
            40, 
            40
        );
        
        return r.intersects(in); 
    } 
    
    private boolean testCollisionSerpentBoule(Serpent s, Boule b)
    {   
        Rectangle r = new Rectangle(  
            (int)b.getCentre().getX(),
            (int)b.getCentre().getY(),
            b.getRayon(),
            b.getRayon()
        );

        Rectangle in = new Rectangle(  
            (int)s.getTete().getPosition().getX(),
            (int)s.getTete().getPosition().getY(),
            s.getAnneau(0).getRayon(), 
            s.getAnneau(0).getRayon()
        ); 
        
        return r.intersects(in); 
    } 
    
    private void    randomGoal(){
        setScore( getScore()+1 );
        Random ran = new Random(); 
        int x = ran.nextInt(500);
        getGoal().getBase().setX( 50 + x );
        x = ran.nextInt(300);
        getGoal().getBase().setY( 50 + x );
    }

     
    
      
    // getters et setters *************************************** ***********
    public void  setSouris(ArrayList<Souris> souris) { this.souris = souris; }  
    public void  setSerpent(Serpent serpent) {  this.serpent = serpent;  } 
    public void setCanal(ArrayList<Anneau> canal) { this.canal = canal; } 
    public void  stop(){ getSerpent().getDeplacement().setVitesse(0); } 
    public void  setFeux(ArrayList<Feu> feux) { this.feux = feux; } 
    public Feu   removeFeu(int i) { Feu f = this.feux.get(i);  this.feux.set(i,null); return f; }  
    public void  setStop(boolean stop) { this.stop = stop; }
    public void  addFeu(Feu feu) { this.feux.add(feu); }  
    public void  setZoo(Zoo rect) {  this.zoo = rect; } 
    public void  setGoal(Goal p){ goal = p; } 
    private void setScore(int s){score = s;} 
     
    public ArrayList<Souris> getSouris() { return souris; } 
    public ArrayList<Feu>  getFeux() { return this.feux; } 
    public ArrayList<Anneau> getCanal() { return canal; }
    public Feu getFeu(int i){ return this.feux.get(i); }  
    public Serpent  getSerpent(){ return serpent; }  
    public boolean  isStop()    { return stop; }
    public Goal     getGoal()   { return goal; } 
    private int     getScore()  {return score;}
    public Zoo      getZoo()    { return zoo; }   


     
}