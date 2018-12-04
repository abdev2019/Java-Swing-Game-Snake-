package outils;



public class Point
{
    private float _x;
    private float _y;  
    

    public Point(float x, float y){
        setX(x);
        setY(y);
    }
    
    public Point(Point p){
        setX(p.getX());
        setY(p.getY());
    }

    public float getX(){ return _x; }
    public float getY(){ return _y; }

    public void setX(float x){ _x = x; }
    public void setY(float y){ _y = y; }
    
    public boolean identique(Point p){
        return 
                p.getY() == getY() &&
                p.getX() == getX();
    }
    
    public boolean identique(Point p, float marge){
        return 
                p.getY()-marge <= getY() && p.getY()+marge >= getY() &&
                p.getX()-marge <= getX() && p.getX()+marge >= getX()  ;
    }
    
    public double getDistance( Point p ){
        return Math.sqrt(
            Math.pow(p.getX()- getX(),2) + 
            Math.pow(p.getY()- getY(),2)
        ); 
    } 
     
    public void mouver(Deplacement dep, Point next){
            float angle = getAngle(next);

            float speedX = dep.getVitesse() * (float)Math.cos(angle); 
            float speedY = dep.getVitesse() * (float)Math.sin(angle); 
            
            setX( getX() + speedX);
            setY( getY() + speedY);  
    }
    
    
    public float getAngle(Point target) {
        return (float)Math.atan2(target.getY() - getY(), target.getX() - getX());
    }
}