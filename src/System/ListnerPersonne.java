/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package System;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 *
 * @author ABDO
 */
public class ListnerPersonne implements MouseListener
{ 
    private Personne pSelected = null; 
    private ArrayList<Personne> personnes;
    Color lastCol;
    

    public void mouseClicked(MouseEvent event) 
    {  
        java.awt.Point mousePosition = new java.awt.Point(event.getX(),event.getY());
 
        for(Personne p : getPersonnes())
        {
            Rectangle rec = new Rectangle(  
                (int)p.getTete().getCentre().getX()-p.getTete().getRayon()/2,
                (int)p.getTete().getCentre().getY(),
                p.getMain().getLongueur(),
                p.getCorps().getHauteur()
            );
            if(rec.contains(mousePosition))
            {
                if( getpSelected() != null )
                    p.setColeur(lastCol);
                
                setpSelected(p);
                lastCol = p.getColeur();
                p.setColeur(Color.white);
                
                return;
            } 
        }  
        if(getpSelected()!=null)
        {
            getpSelected().setColeur(lastCol);
            setpSelected(null);
        }
    }

    public void mousePressed(MouseEvent e) {} 
    public void mouseReleased(MouseEvent e) {} 
    public void mouseEntered(MouseEvent e) {} 
    public void mouseExited(MouseEvent e) {} 
    
    
    
    public Personne getpSelected() {
        return pSelected;
    }

    public void setpSelected(Personne pSelected) {
        this.pSelected = pSelected;
    }

    public ArrayList<Personne> getPersonnes() {
        return personnes;
    }

    public void setPersonnes(ArrayList<Personne> personnes) {
        this.personnes = personnes;
    }
}
