
package boule;
 
import System.Mur;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JPanel;
import outils.Point;
import outils.Rect;



public class Game extends JPanel implements Runnable
{
    Thread thread;
    ArrayList<Boule> boules = new ArrayList();
    
    Mur floor; 
     
    
    public Game()
    {  
        thread = new Thread(this);
        this.setLayout(null);
        this.setBounds(0, 0, 1000, 700 ); 
        this.floor = new Mur(new Point(0,500),1000,100, Color.darkGray);
    }
    
    public void startGame()
    {
        this.thread.start();
    }
     
    
    @Override
    public void run() 
    {  
            while(true)
            {  
                //this.repaint();
                try{ Thread.sleep(20); } catch (Exception e) {}
            } 
    } 
   
    

    
    @Override public void paint(Graphics g)
    { 
        floor.dessiner( (Graphics2D)g );
        for(int i=0;i<boules.size();i++)
        boules.get(i).dessiner(g); 
    }
    


    public ArrayList<Boule> getBoules() {
        return boules;
    }

    public void setBoules(ArrayList<Boule> boules) {
        this.boules = boules;
    }
    
    
}
