
package outils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

import game.Program;

/**
 *
 * @author ABDO
 */


public class Image 
{   
    private String chemin;   
    private java.awt.image.BufferedImage image;

    
    

    public Image(String chemin) {
        this.setChemin(chemin);  
    }
    
    
    
    private void chargerImage(){
        try{  
            set(ImageIO.read(new File(Program.class.getResource(chemin).toURI()))); 
        }
        catch(Exception e){
            set(null); e.printStackTrace();
            System.out.println("Charmement image echoue !\nChemin : "+getChemin());
        } 
    }
    
      
    
 
 
    public String getChemin() {
        return chemin;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
        chargerImage();
    }

    public BufferedImage get() {
        return image;
    }

    public void set(BufferedImage image) {
        this.image = image;
    } 
    
}
