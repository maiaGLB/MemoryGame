/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linuxmemo.Vista;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import linuxmemo.Control.Scores;
import linuxmemo.Modelo.Jugador;

public class InterScores extends JPanel implements ActionListener, Observer
{
    protected Scores scor;
    protected JFrame jscor;
    JTable jpunt;
    JScrollPane js;
    Observable gam;
    
    
    public InterScores( ) 
    {
        this.jscor = new JFrame("Historial de Puntajes");
        this.jscor.setBounds(100,100,300,200);
        this.jscor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        this.scor = new Scores();
        this.mostrar();
        
        this.jscor.setVisible(false);
    }
    
    public void setGam( Observable gam )
    {
        this.gam = gam;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    public void ocultar() 
    {
        this.jscor.setVisible(false);
    }
    
    private void mostrar() 
    {
        try 
        {
            this.jpunt = new JTable(
                    this.scor.getDataVector(),
                    this.scor.getNamesVector());
            this.js = new JScrollPane(this.jpunt);
            this.jscor.getContentPane().add(this.js);

            this.jscor.setVisible(true);
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void save( Jugador jug ) 
    {
        try 
        {
            this.scor.saveJug(jug);
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
    }
    
    @Override
    public void update(Observable o, Object arg) 
    {
        try 
        {
            if ( gam.equals(o) ) 
            {
                this.save( (Jugador)arg );
                this.mostrar();
            }
        }
        catch( Exception e)
        {
            e.printStackTrace();
        }
    }

    public Scores getScor() {
        return this.scor;
    }
    
}
