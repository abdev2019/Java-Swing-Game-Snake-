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

public class ListnerPorte  implements MouseListener
{ 
    private Batiment batiment;  
    Color lastCol;
    private Porte porteSelected;

    public void mouseClicked(MouseEvent event) 
    {  
        java.awt.Point mousePosition = new java.awt.Point(event.getX(),event.getY());
 
        for(Etage e : getBatiment().getEtages())
        {
            Rectangle rec = new Rectangle(  
                (int)e.getPorte().getBase().getX(),
                (int)e.getPorte().getBase().getY(),
                e.getPorte().getLongueur(),
                e.getPorte().getHauteur()
            );
            if(rec.contains(mousePosition))
            {  
                if(getPorteSelected()!=null) 
                    getPorteSelected().setColeur(lastCol);
                setLastCol( e.getPorte().getColeur() );
                e.getPorte().setColeur(Color.yellow);
                setPorteSelected(e.getPorte());
                e.getPorte().ouvrir();
                return;
            } 
        }

        Rectangle rec = new Rectangle(  
            (int)getBatiment().getRdc().getPorte().getBase().getX(),
            (int)getBatiment().getRdc().getPorte().getBase().getY(),
            getBatiment().getRdc().getPorte().getLongueur(),
            getBatiment().getRdc().getPorte().getHauteur()
        );
        if(rec.contains(mousePosition))
        {  
            if(getPorteSelected()!=null) 
                getPorteSelected().setColeur(lastCol); 
            setLastCol( getBatiment().getRdc().getPorte().getColeur() );
            getBatiment().getRdc().getPorte().setColeur(Color.yellow);
            setPorteSelected(getBatiment().getRdc().getPorte());
            getBatiment().getRdc().getPorte().ouvrir();
            return;
        } 
        
        Rectangle rec2 = new Rectangle(  
            (int)getBatiment().getAscenceur().getCabinet().getPorte().getBase().getX(),
            (int)getBatiment().getAscenceur().getCabinet().getPorte().getBase().getY(),
            getBatiment().getAscenceur().getCabinet().getPorte().getLongueur(),
            getBatiment().getAscenceur().getCabinet().getPorte().getHauteur()
        );
        if(rec2.contains(mousePosition))
        {  
            if(getPorteSelected()!=null) 
                getPorteSelected().setColeur(lastCol); 
            setLastCol( getBatiment().getAscenceur().getCabinet().getPorte().getColeur() );
            getBatiment().getAscenceur().getCabinet().getPorte().setColeur(Color.yellow);
            setPorteSelected( getBatiment().getAscenceur().getCabinet().getPorte() );
            getBatiment().getAscenceur().getCabinet().ouvrirPorte();
            return;
        } 

        if(getPorteSelected()!=null)
        {
            getPorteSelected().setColeur(lastCol);
            setPorteSelected(null);
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

    public Color getLastCol() {
        return lastCol;
    }

    public void setLastCol(Color lastCol) {
        this.lastCol = lastCol;
    }

    public Porte getPorteSelected() {
        return porteSelected;
    }

    public void setPorteSelected(Porte porteSelected) {
        this.porteSelected = porteSelected;
    }
 
    
    
    
}
