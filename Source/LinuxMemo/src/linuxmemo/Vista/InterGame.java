
package linuxmemo.Vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import linuxmemo.Control.Game;

public class InterGame extends JPanel implements ActionListener
{      
      
    //PANEL INFERIOR Y CENTRAL
    JPanel sur = new JPanel(new FlowLayout());
    JPanel centro = new JPanel(new GridLayout(4, 4, 4, 4));    
    JPanel norte = new JPanel(new FlowLayout());
    
    //CAMPOS DE TEXTO NO EDITABLE MUESTRA LAS PAREJAS RESTANTES
    JTextField txtParejas = new JTextField(3); 
    JTextField txtIntentos = new JTextField(3);      
    
    //ETIQUETAS
    JLabel nParejas = new JLabel("Parejas restantes:");  
    JLabel nIntentos = new JLabel("Intentos fallidos : ");
    
    //MATRIZ DE BOTONES
    JButton[][] botones = new JButton[4][4];
        
    //MATRIZ DE IMAGENES PARA LOS ICONOS
    ImageIcon imagenesBtn[][] = new ImageIcon[4][4];
    ImageIcon defecto = new ImageIcon(getClass().getResource("oculta.jpg"));
    
    protected Game gam;
    
    public InterGame( Observer iscor) 
    {   
        // inicio el juego
        this.newgame( iscor );
        
        this.setLayout(new BorderLayout());
        
        // agrego componentes al panel sur     
        sur.add(nIntentos);
        sur.add(txtIntentos);
        
        norte.add(nParejas);
        norte.add(txtParejas);
        
        // asigno atributos a las cajas de texto
        txtIntentos.setEditable(false);
        txtParejas.setEditable(false);
        
        // asigna los iconos para los botones
System.out.println(imagenesBtn.length);
        for (int i = 0; i < imagenesBtn.length; i++) 
        {
            for (int j = 0; j < imagenesBtn[i].length; j++) 
            {   
                String img = gam.getTablero().getFicha(i, j).getImg();
System.out.println(img);
                imagenesBtn[i][j] = new ImageIcon(getClass().getResource(img));
            }
        }
        // completa la matriz de botones y asigna eventos
        for (int i = 0; i < botones.length; i++) 
        {
            for (int j = 0; j < botones[i].length; j++) 
            {
                botones[i][j] = new JButton();
                botones[i][j].setBackground(null);
                botones[i][j].setIcon(defecto);
                botones[i][j].addActionListener(this);
                centro.add(botones[i][j]);
            }
        }
 
        this.add(norte, BorderLayout.NORTH);
        this.add(centro, BorderLayout.CENTER);
        this.add(sur, BorderLayout.SOUTH);
        
        this.setVisible(true);
    } 
    
    int click = 0;
    boolean out = false;
    
    // metodo que tiene la logica del juego
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        for (int i = 0; i < botones.length; i++) 
        {          
            for (int j = 0; j < botones[i].length; j++) 
            {             
                if (e.getSource() == botones[i][j]) 
                {                   
                    if (botones[i][j].getIcon().equals(defecto) ) 
                    {                      
                        botones[i][j].setIcon(imagenesBtn[i][j]);
                        
                        this.click ++;
                        this.out = this.getGam().comprobarFicha(i, j);
                       
                        if ( this.out == true && this.click <= 2 ) 
                        {
                            this.click = 0;
                            
                             JOptionPane.showMessageDialog(null,"Pareja Encontrada","LinuxMemo", JOptionPane.PLAIN_MESSAGE);
                             botones[this.getGam().getF1i()][this.getGam().getF1j()].setIcon(imagenesBtn[this.getGam().getF1i()][this.getGam().getF1j()]);
                             botones[this.getGam().getF2i()][this.getGam().getF2j()].setIcon(imagenesBtn[this.getGam().getF2i()][this.getGam().getF2j()]);
                             txtParejas.setText(Integer.toString(this.getGam().mostrarParesRestantes()));
                             System.out.println("Parejas restantes: " + this.getGam().mostrarParesRestantes());
                             
                             if ( this.getGam().comprobarFin() == true ) 
                             {
                                 if( this.getGam().mostrarErrores() <= 6 )
                                 {
                                    JOptionPane.showMessageDialog(null,"Puntaje: "+this.getGam().calcularPuntaje()+"\n"+"Juego Excelente.."+"\n"+" FELICIDADES..!! "+"\n"+ this.getGam().getJug().getName(),"LinuxMemo - [ Resultado ]", JOptionPane.PLAIN_MESSAGE);	
                                 }
                                 else if(this.getGam().mostrarErrores() <= 8)
                                 {
                                    JOptionPane.showMessageDialog(null,"Puntaje: "+this.getGam().calcularPuntaje()+"\n"+"Juego Bueno !!","LinuxMemo - [ Resultado ]", JOptionPane.PLAIN_MESSAGE);	
                                 }
                                 else if(this.getGam().mostrarErrores() <= 12)
                                 {
                                    JOptionPane.showMessageDialog(null,"Puntaje: "+this.getGam().calcularPuntaje()+"\n"+"Juego Regular !!","LinuxMemo - [ Resultado ]", JOptionPane.PLAIN_MESSAGE);	
                                 }
                                 else
                                 {
                                    JOptionPane.showMessageDialog(null,"Puntaje: "+this.getGam().calcularPuntaje()+"\n"+"Almenos lo Terminaste","LinuxMemo - [ Resultado ]", JOptionPane.PLAIN_MESSAGE);	
                                 }
                             } 
                        } 
                        else if ( click == 2 && out == false )
                        {
                             this.click = 0;
                            
                             JOptionPane.showMessageDialog(null,"Pareja Equivocada","MemoLinux", JOptionPane.PLAIN_MESSAGE);
                             botones[this.getGam().getF1i()][this.getGam().getF1j()].setIcon(defecto);
                             botones[this.getGam().getF2i()][this.getGam().getF2j()].setIcon(defecto);
                             
                             this.getGam().reiniciarPosiciones();
                             
                             txtIntentos.setText(Integer.toString(this.getGam().mostrarErrores()));
                             System.out.println("Numero de intentos fallidos: " + this.getGam().mostrarErrores());
                        }
                    }
                }
            }
        }
    }
    
    // metodo iniciador del juego que pide el nombre del jugador y llama a dos thread
    public void newgame( Observer iscor ) 
    {   
        String jug = "";
        jug = JOptionPane.showInputDialog(null,"Nombre del nuevo Jugador","LinuxMemo", JOptionPane.PLAIN_MESSAGE);
        this.gam = new Game(jug, iscor );
        
        // iniciamos el juego y ubicamos las fichas al mismo tiempo
        // el juego termina de crearse cuando las fichas son ubicadas
        new Thread(this.getGam(), "inciarGame").start();
        new Thread(this.getGam().getTablero(), "ubicarFichas").start();

    }

    public Game getGam() {
        return gam;
    }
   
}
