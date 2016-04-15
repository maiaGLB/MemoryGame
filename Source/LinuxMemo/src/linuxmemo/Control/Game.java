
package linuxmemo.Control;

import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import linuxmemo.Modelo.Jugador;
import linuxmemo.Modelo.Tablero;

public class Game extends Observable implements Runnable 
{
    private Tablero tablero;
    protected Jugador jug;
  
    // nro de pares dados vuelta que da el jugador 
    private int ParGirado = 0;
    // nro de pares dados vuelta equivocados
    private int ParErroneo = 0;
    // nro de pares correctos totales
    private int ParOKTotal = 8;
    
    protected int f1i = -1;
    protected int f1j = -1;
    protected int f2i = -1; 
    protected int f2j = -1;
    
    protected Scores observado;
    
    public Game( String nameJug, Observer interScor ) 
    {
        // asocio InterScore, el observador con el observable InterGame
        this.addObserver( interScor );
        
        this.jug = new Jugador(nameJug, 0);
        this.tablero = new Tablero();
    }
    
    @Override
    synchronized public void run() 
    {
        this.reiniciarPosiciones();
        this.ParGirado = 0;
        this.ParErroneo = 0;
        this.ParOKTotal = 8;
        while( !this.tablero.isArmado() ) 
        {
                try 
                {
                    wait();
                } 
                catch (InterruptedException ex) 
                {
                    Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
    }
    
    @Override
    public boolean equals( Object o )
    {
        // son de la misma clase?
        if( o.getClass() != this.getClass() )
            return false;
        // somos la misma instancia?
        if( o == this )
            return true;
        // casteo para comprobar dato por dato sin son iguales 
        // ya que no son la misma instancia
        Game aux = (Game)o;
        if( this.ParErroneo != aux.ParErroneo )
            return false;
        if( this.ParGirado != aux.ParGirado )
            return false;
        if( this.ParOKTotal != aux.ParOKTotal )
            return false;
        if( this.f1i != aux.f1i )
            return false;
        if( this.f1j != aux.f1j )
            return false;
        if( this.f2i != aux.f2i )
            return false;
        if( this.f2j != aux.f2j )
            return false;
        if( !this.equals(aux.tablero) )
            return false;
        if( !this.equals(aux.jug) )
            return false;
        return true;
    }
    
    public int calcularPuntaje() 
    {
        return this.ParGirado;
    }
    
    public int mostrarParesRestantes() 
    {
        return this.ParOKTotal;
    }
    
    public int mostrarErrores() 
    {
        return this.ParErroneo;
    }
    
    public boolean comprobarFicha( int i, int j) 
    {
        if (this.f1i == -1) 
        {
            this.girarFicha(i, j);
            this.f1i = i;
            this.f1j = j;
        } 
        else if (this.f2i == -1) 
        {
            this.girarFicha(i, j);
            this.f2i = i;
            this.f2j = j;
            this.ParGirado ++;
            return this.comprabarPar();
        } 
        return false;
    }
    
    public boolean comprobarFin()
    {
        for (int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 4; j++ ) {
                if ( this.getTablero().getFicha(i, j).getEstado() == false )
                {
                    this.reiniciarPosiciones();
                    return false;
                }
            }
        }
        // Puntaje del jugador a guardar
        this.jug.setScore( this.calcularPuntaje() );
        
        clearChanged();
        //Informo Fin Juego.
        setChanged();
        notifyObservers( this.jug );
        return true;
    }
    
    private boolean comprabarPar() 
    {
        if ( this.getTablero().getFicha(this.f1i, this.f1j).getNro() != this.getTablero().getFicha(this.f2i, this.f2j).getNro() ) 
        {
            this.ParErroneo ++;
            return false;
        } 
        else 
        {
            this.ParOKTotal --;
            this.getTablero().getFicha(this.f1i, this.f1j).setEstado(true);
            this.getTablero().getFicha(this.f2i, this.f2j).setEstado(true);
        }
        return true;
    }
    
    private void girarFicha( int i, int j) 
    {
        boolean estado = this.getTablero().getFicha(i, j).getEstado();
        // !estado si es true lo cambia a false y viceversa
        this.getTablero().getFicha(i,j).setEstado(!estado);
    }
    
    public void reiniciarPosiciones() 
    {
        this.setF1i(-1);
        this.setF1j(-1);
        this.setF2i(-1);
        this.setF2j(-1);
    }

    public Tablero getTablero() {
        return this.tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }
    
    public int getF1i() {
        return this.f1i;
    }

    public int getF1j() {
        return this.f1j;
    }
    
    public int getF2i() {
        return this.f2i;
    }

    public int getF2j() {
        return this.f2j;
    }

    public void setF1i(int f1i) {
        this.f1i = f1i;
    }

    public void setF1j(int f1j) {
        this.f1j = f1j;
    }

    public void setF2i(int f2i) {
        this.f2i = f2i;
    }

    public void setF2j(int f2j) {
        this.f2j = f2j;
    }

    public void addObserver(Scores scor) {
        this.observado = scor;
    }

    public Jugador getJug() {
        return this.jug;
    }

    public void setJug(Jugador jug) {
        this.jug = jug;
    }
}
