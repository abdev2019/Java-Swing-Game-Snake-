package serpent;
  
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Shape;
import java.awt.geom.Path2D;
import outils.Deplacement;
import outils.Image;  
import java.net.URL; 
import outils.Point;


/**
 *
 * @author ABDO
 */


public class Feu implements Runnable
{  
    private static Image image;
    private Point        position;
    private Deplacement  dep;  
     
    
    
    
    public Feu(Point position) { 
        this.setPosition(position);
        this.setDep(new Deplacement(10,null));   
    }

    @Override public void run()
    {   
        AudioClip audio = playAudio();
        while(true){
            getPosition().mouver(getDep(), getDep().getFin());  
            if(isArrive()) { 
                audio.stop();
                audio = null;
                return;
            }
            try{Thread.sleep(20);}catch(Exception e){} 
        }   
    }
    
    public void start(){
        new Thread(this).start(); 
    }

    
    // graphic
    public void dessiner(java.awt.Graphics2D g){ 
        if( getImage()!=null)
            g.drawImage(
                getImage().get(), 
                (int)getPosition().getX(), 
                (int)getPosition().getY(), 
                30,
                30,
                null
            );
        
        else{ 
            g.setColor( java.awt.Color.red ); 
            g.fill(createStar(getPosition().getX(), getPosition().getY(), 30, 10, 15, -50));
        }
    }

 
    
    // chargement d'image
    public static void chargerImage(){
        String chemin = "/res/feu.png";
        setImage(new Image(chemin));
    }
    
  

    // getters & setters 
    public Deplacement getDep() {
        return dep;
    }
    public void setDep(Deplacement dep) {
        this.dep = dep;
    }
    public static Image getImage() {
        return image;
    }
    public static void setImage(Image image) {
        Feu.image = image;
    }
    public Point getPosition() {
        return position;
    }
    public void setPosition(Point position) {
        this.position = new Point(position);
    } 
    public boolean isArrive(){
        java.awt.Rectangle r = new java.awt.Rectangle(  
            (int)getPosition().getX(),
            (int)getPosition().getY(),
            10,
            10
        );
        
        java.awt.Rectangle in = new java.awt.Rectangle(  
            (int)getDep().getFin().getX(),
            (int)getDep().getFin().getY(),
            getDep().getVitesse(), 
            getDep().getVitesse()
        );
        
        return r.intersects(in);  
    }
 
    private AudioClip playAudio(){  
        String ch = "/res/burning.wav";
        try {  
            URL url = new URL(ch);
            AudioClip clip = Applet.newAudioClip(url);
            clip.play();
            return clip;
        }
        catch(Exception ex){}
        return null;
    }


    private static Shape createStar(double x, double y, double innerRadius, double outerRadius, int numRays,double startAngleRad)
    {
        Path2D path = new Path2D.Double();
        double deltaAngleRad = Math.PI / numRays;
        for (int i = 0; i < numRays * 2; i++)
        {
            double angleRad = startAngleRad + i * deltaAngleRad;
            double ca = Math.cos(angleRad);
            double sa = Math.sin(angleRad);
            double relX = ca;
            double relY = sa;
            if ((i & 1) == 0)
            {
                relX *= outerRadius;
                relY *= outerRadius;
            }
            else
            {
                relX *= innerRadius;
                relY *= innerRadius;
            }
            if (i == 0)
            {
                path.moveTo(x + relX, y + relY);
            }
            else
            {
                path.lineTo(x + relX, y + relY);
            }
        }
        path.closePath();
        return path;
    }    
}

















