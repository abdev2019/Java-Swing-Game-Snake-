package serpent;
 
import outils.Rect;
import outils.Image;




public class Zoo
{  
    private Image image;
    private Rect surface;
    

    public Zoo(Rect surface, String cheminImage) { 
        this.setImage( new Image(cheminImage) ); 
        this.setSurface(surface);
    } 
    
    
    

    public Rect getSurface() {
        return surface;
    }

    public void setSurface(Rect surface) {
        this.surface = surface;
    }
    
    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
    
    public void dessiner(java.awt.Graphics2D g){
        if(getImage().get() != null)
            g.drawImage(
                getImage().get(), 
                (int)getSurface().getBase().getX(), 
                (int)getSurface().getBase().getY(), 
                getSurface().getLongueur(),
                getSurface().getHauteur(),
                null
            );
        
        else{
            g.setColor( new java.awt.Color(50,100,50) );
            g.fillRect(
                (int)getSurface().getBase().getX(),
                (int)getSurface().getBase().getY(), 
                getSurface().getLongueur(),
                getSurface().getHauteur()
            ); 
        }
        
        g.setColor( java.awt.Color.white );
        g.drawRect(
            (int)getSurface().getBase().getX(),
            (int)getSurface().getBase().getY(), 
            getSurface().getLongueur(),
            getSurface().getHauteur()
        ); 
    }
}
