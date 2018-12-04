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


/**
 *
 * @author ABDO
 */

public class ListnerEtage  implements MouseListener
{ 
    private Batiment batiment;
    private Etage    eSelected = null; 
    Color lastCol;

    public void mouseClicked(MouseEvent event) 
    {  
        java.awt.Point mousePosition = new java.awt.Point(event.getX(),event.getY());
 
        for(Etage e : getBatiment().getEtages())
        {
            Rectangle rec = new Rectangle(  
                (int)e.getBase().getX(),
                (int)e.getBase().getY(),
                e.getLongueur(),
                e.getHauteur()
            );
            if(rec.contains(mousePosition))
            { 
                if(geteSelected()!=null)
                    geteSelected().setColeur(lastCol);
                lastCol = e.getColeur();
                e.setColeur(Color.gray);
                seteSelected(e);
                return;
            }
        }  
        if(geteSelected()!=null)
        {
            geteSelected().setColeur(lastCol);
            seteSelected(null);
        }
    }

    public void mousePressed(MouseEvent e) {} 
    public void mouseReleased(MouseEvent e) {} 
    public void mouseEntered(MouseEvent e) {} 
    public void mouseExited(MouseEvent e) {} 

    public Batiment getBatiment() {
        return batiment;
    }

    public void setBatiment(Batiment batiment) {
        this.batiment = batiment;
    }

    public Etage geteSelected() {
        return eSelected;
    }

    public void seteSelected(Etage eSelected) {
        this.eSelected = eSelected;
    }
    
    
}
