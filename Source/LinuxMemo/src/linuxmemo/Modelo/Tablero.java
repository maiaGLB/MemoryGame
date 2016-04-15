
package linuxmemo.Modelo;

import java.util.ArrayList;
import java.util.Random;

public class Tablero implements Runnable
{
    private Ficha[][] tablero = null;
    private boolean armado = false;
    
    public Tablero() 
    {
        //inicializamos el tablero
        tablero = new Ficha[4][4];
        armado = false;
    }
    
    @Override
    public boolean equals( Object o )
    {
        if( o.getClass() != this.getClass() )
            return false;
        if( o == this )
            return true;
        Tablero tab = (Tablero)o;
        if( this.armado != tab.armado )
            return false;
        return true;
    }
    
    @Override
    synchronized public void run() {
        // var que almacenara el valor del random
        Random r = new Random();
        
        //metodo que carga las img en un arreglo
        ArrayList<String> al = this.cargarImg();
        
       //se genera un random entre los elementos disponibles del array
        //asignando en pares las fichas al tablero
        for (int i = 0; i < 4; i++ ) {
            for ( int j = 0; j < 4; j++ ) {
                if ( al.size() > 0 ) {
                    int cant = al.size();
                    int pos;
                    if ( cant > 1 ) {
                        //asigna un nro random igual o menor a la cantidad de elementos en el arreglo -1
                        pos = r.nextInt( cant-1 );
                    } else {
                        pos = 0;
                    }
                    String arch_name = al.get( pos );
                    // nos quedamos con el primer caracter y lo convertimos a integer
                    int num_ficha = Integer.parseInt( arch_name.substring(0, 1) );
                    
                    this.tablero[i][j] = new Ficha( arch_name, num_ficha, false );
                    
                    al.remove( pos );
                }
            }
        }
        this.armado= true;
        notifyAll(); 
    } 
    
    private ArrayList<String> cargarImg()
    {
        ArrayList<String> al = new ArrayList<String>();
        for ( int i = 0; i < 2; i++ ) {
            for ( int j = 1; j < 9; j++) {
                al.add( j + ".png" );
            }
        }
        return al;
    }
    
    public Ficha getFicha( int i, int j) 
    {
        return this.tablero[i][j];
    }
    
    public Ficha[][] getTablero() 
    {
        return tablero;
    }

    public void setTablero(Ficha[][] tablero) {
        this.tablero = tablero;
    }

    public boolean isArmado() {
        return armado;
    }

}
