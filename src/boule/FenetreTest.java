package boule;

import System.PanneauBatiment;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame; 
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import outils.Point;
import serpent.PanneauSerpent;


public class FenetreTest extends JFrame implements ActionListener
{ 
    
    JPanel panneauGame;
    Game game;
    
    JButton btn = new JButton("Add boule");
    
    public FenetreTest(Game game)
    {
        this.setLayout(null);
        this.setVisible(true);  
        this.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE); 
        this.setBounds(0, 0, 1000, 700 ); 
        this.getContentPane().setBackground(Color.white); 
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.game = game;
        init();
    }
    
    private void init()
    {   
        Border eBorder = new EtchedBorder(Color.RED, Color.PINK);
        panneauGame = new JPanel(); 
        panneauGame.setBorder(BorderFactory.createTitledBorder(eBorder, "Boule"));  
        panneauGame.setLayout(new BorderLayout());
        panneauGame.setBounds(20,100,890,570); 
        panneauGame.add(game);
        this.add(panneauGame);
        
        btn.addActionListener(this);
        btn.setBounds(20,20,200,40);
        add(btn);
    } 

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        int X = (new Random()).nextInt(game.getWidth());
        Boule b = new Boule(new Point(X,100),50,Color.red, 10);
        this.game.getBoules().add(b);
    }
}


