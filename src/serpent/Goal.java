
package serpent;

import java.awt.Color;
import outils.Point;
import outils.Rect;






public class Goal extends Rect
{
    private Color coleur;
    
    public Goal(Point base, int w, int h, Color clr) {
        super(base, w, h);
        this.setColeur(clr);
    }

    public Color getColeur() {
        return coleur;
    }

    public void setColeur(Color coleur) {
        this.coleur = coleur;
    }
    
    
    
    public void dessiner(java.awt.Graphics2D g){
        g.setColor( this.getColeur() );
        g.fillRect(
            (int)this.getBase().getX(), 
            (int)this.getBase().getY(),
            this.getLongueur(), 
            this.getHauteur()
        );
    }
}
