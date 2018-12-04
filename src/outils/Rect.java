 
package outils;



public class Rect 
{ 
    private Point base;
    private int longueur;
    private int hauteur;
    
    
    
    public Rect(Point base, int w, int h)
    {
        setBase(base);
        setLongueur(w);
        setHauteur(h);
    }
    
    public void setBase(Point base) {
        this.base = base;
    }

    public void setLongueur(int longeur) {
        this.longueur = longeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public Point getBase() {
        return base;
    }

    public int getLongueur() {
        return longueur;
    }

    public int getHauteur() {
        return hauteur;
    } 
     
}
