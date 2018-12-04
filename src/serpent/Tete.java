package serpent;
 
import outils.Image;
import outils.Point;





public class Tete 
{ 
    private Point position; 
    private Image image;
    
    
    public Tete(Point position, String chemin){ 
        setPosition(position);
        setImage( new Image(chemin) );
    }
    


     
    
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = position;
    }
    public Image getImage() {
        return image;
    }   
    public void setImage(Image img) {
        image = img;
    } 
    
    // graphic
    public void dessiner(java.awt.Graphics2D g){ 
        if(getImage()!=null)
            g.drawImage(
                getImage().get(), 
                (int)getPosition().getX(), 
                (int)getPosition().getY(), 
                40,
                40,
                null
            );
        
        else{
            g.setColor( java.awt.Color.blue );
            g.fillOval(
                (int)getPosition().getX(),
                (int)getPosition().getY(), 
                40, 
                40
            ); 
        }
    }

}
