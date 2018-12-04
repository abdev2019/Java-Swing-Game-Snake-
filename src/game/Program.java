package game;
 
import serpent.PanneauSerpent;
import System.*; 
import boule.FenetreTest;
import boule.Game;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import serpent.*;
import outils.Point;
import outils.Rect; 




 


public class Program 
{
    public static void main(String[] args) 
    {      
        /*
        Game game = new Game();
        game.startGame();
        new FenetreTest(game); */
        
        
        
        // Serpent ************************************* 
            String chemin = "/res/tete.png"; 
            Tete  tete = new Tete(new Point(400,110),chemin);            
            Serpent serp = new Serpent(tete,10, java.awt.Color.GREEN);   
             
            
            
        // Zoo ******   
            chemin = "/res/zoo.jpg";
            Zoo zoo = new Zoo(new Rect(new Point(0,0),800,350),chemin);
            
        
        // batiments **** 
            Batiment bat = new Batiment(800, 380, new Point(10,10),Color.ORANGE,3); 
            PanneauBatiment panBatiment = new PanneauBatiment(800,380, bat); 
            
             
        // Game ******
            PanneauSerpent panSerpent = new PanneauSerpent(serp, zoo );  
            panSerpent.start();  
            
            
            
        // IHM   
            Fenetre f = new Fenetre(835, 800, panSerpent, panBatiment); 
         
    } 
}
