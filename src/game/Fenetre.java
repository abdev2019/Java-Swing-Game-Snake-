 
package game;

import serpent.PanneauSerpent;
import System.*;
import boule.Boule;
 
import serpent.Feu;
import outils.Point; 
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JFrame; 

 

public class Fenetre extends JFrame
{
    private JButton btnStart; 
    private PanneauSerpent  panneauSerpent;
    private PanneauBatiment panneauBatiment;
    
    public Fenetre(int w, int h, PanneauSerpent ps, PanneauBatiment pb)
    {
        this.setLayout(null);
        this.setVisible(true);  
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); 
        this.setBounds(0, 0, w, h ); 
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(Color.black);
        
        this.setPanneauBatiment(pb);
        this.setPanneauSerpent(ps);  
        this.initListner(); 
        
        ps.creerCanal( new Point(ps.getWidth()/2 ,ps.getY()+ps.getHeight()-10),35, 5 );
        
        
        
        //this.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH); 
        //btnStart = new javax.swing.JButton("Start");
        //btnStart.setBounds(10, 10, 100, 40); 
        //this.add(btnStart);  
    }
    

    
    public void setPanneauSerpent(PanneauSerpent g){ 
        panneauSerpent = g;  
        this.add(panneauSerpent);
    }
    
    public PanneauSerpent getPanneauSerpent(){ 
        return panneauSerpent;  
    } 

    public PanneauBatiment getPanneauBatiment() {
        return panneauBatiment;
    }

    public void setPanneauBatiment(PanneauBatiment panBat) {
        this.panneauBatiment = panBat;
        this.add(panBat);
    }
    

    public void initListner()
    {  
        KeyListener KeyBoardListener = new KeyListener()
        {
            @Override public void keyPressed(KeyEvent args0) 
            {
                int key = args0.getKeyCode();  ; 
                
                switch(key)
                {
                    case (KeyEvent.VK_LEFT) :
                    {   
                        if(    
                            getPanneauSerpent().getSerpent().getTete().getPosition().getY()
                                            !=
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().getY()
                        )
                        {   
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setX(0);
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setY(
                                getPanneauSerpent().getSerpent().getTete().getPosition().getY()
                            ); 
                        }
                        break;
                    }

                    case (KeyEvent.VK_RIGHT):
                    {  
                        if( 
                            getPanneauSerpent().getSerpent().getTete().getPosition().getY()
                                            !=
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().getY()  
                        )
                        {  
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setX(getPanneauSerpent().getZoo().getSurface().getLongueur()); 
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setY(getPanneauSerpent().getSerpent().getTete().getPosition().getY());  
                        }
                        break;
                    }

                    case (KeyEvent.VK_UP):
                    {  
                        if(  
                            getPanneauSerpent().getSerpent().getTete().getPosition().getX()
                                            !=
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().getX()
                        ) 
                        {   
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setY(0); 
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setX(getPanneauSerpent().getSerpent().getTete().getPosition().getX());
                        }
                        break;
                    }

                    case (KeyEvent.VK_DOWN):
                    {     
                        if(
                            getPanneauSerpent().getSerpent().getTete().getPosition().getX()
                                            !=
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().getX()
                        )
                        {  
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setY(getPanneauSerpent().getZoo().getSurface().getHauteur());
                            getPanneauSerpent().getSerpent().getDeplacement().getFin().setX(getPanneauSerpent().getSerpent().getTete().getPosition().getX());
                        } 
                        
                        break;
                    }

                    case (KeyEvent.VK_A)        : 
                        getPanneauSerpent().getSerpent().getDeplacement().setVitesse( 
                            getPanneauSerpent().getSerpent().getDeplacement().getVitesse()+1
                        );  
                        for(int i=0; i<getPanneauSerpent().serpents.size(); i++)
                           getPanneauSerpent().serpents.get(i).getDeplacement().setVitesse( 
                                getPanneauSerpent().serpents.get(i).getDeplacement().getVitesse()+1
                            );  
                        break;
                        
                    case (KeyEvent.VK_Z)        : 
                        getPanneauSerpent().getSerpent().getDeplacement().setVitesse( 
                            getPanneauSerpent().getSerpent().getDeplacement().getVitesse()-1 
                        ); 
                        for(int i=0; i<getPanneauSerpent().serpents.size(); i++)
                            getPanneauSerpent().serpents.get(i).getDeplacement().setVitesse( 
                                getPanneauSerpent().serpents.get(i).getDeplacement().getVitesse()-1
                            ); 
                        break;
                        
                    case (KeyEvent.VK_S)  : 
                        getPanneauSerpent().setStop( !getPanneauSerpent().isStop() ); 
                        break;
                        
                    case (KeyEvent.VK_R):
                    {  
                        getPanneauSerpent().getSerpent().getTete().setPosition(new Point(500,200)); 
                        getPanneauSerpent().getSerpent().initAnneaux();
                        getPanneauSerpent().start();
                        break;
                    }
                    case (KeyEvent.VK_F) : 
                    {
                        Feu f = getPanneauSerpent().getSerpent().jeterFeu();
                        getPanneauSerpent().addFeu(f); 
                        f.start();
                        break; 
                    }  
                    case (KeyEvent.VK_N) : 
                    {
                        getPanneauSerpent().ajouterSerpent();
                        break; 
                    }  
                    
                    // pan2
                    case KeyEvent.VK_O : 
                    {
                        getPanneauBatiment().getBatiment().getAscenceur().getCabinet().ouvrirPorte();
                        break;
                    }

                    case KeyEvent.VK_D : 
                    {
                        getPanneauBatiment().demarerJeu();
                        break;
                    }
                    
                    case KeyEvent.VK_B:
                    { 
                        int X = (new Random()).nextInt(getPanneauSerpent().getWidth());
                        Boule b = new Boule(new Point(X,0),30,Color.red, 10);
                        getPanneauSerpent().getBalles().add(b);  
                        b.jeter(getPanneauSerpent().floor);
                    }
                }
            }   

            @Override public void keyReleased(KeyEvent args0) {  } 
            @Override public void keyTyped(KeyEvent args0) {}
        };
          
        
        this.addKeyListener(KeyBoardListener); 
    } 

    
        
    // tools *******************************************************
    public static int xToPercent(int x, int w){
        if(x<=0) return 0;
        if(x>100) x=100;
        x = (int)(w/ (100/(float)x) );
        return x;
    }
    public static int yToPercent(int y, int h){
        if(y<=0) return 0;
        if(y>100) y=100;
        y = (h/ (100/y) );
        return y;
    }
    
}
