/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linuxmemo.Modelo;

/**
 *
 * @author mai
 */
public class Jugador 
{
    protected String name = "";
    protected int score = 0;
    
    public Jugador( String name, int score ) 
    {
        this.name = name;
        this.score = score;
    }
    
    @Override
    public boolean equals( Object o )
    {
        if( o.getClass() != this.getClass() )
            return false;
        if( o == this )
            return true;
        Jugador jug = (Jugador)o;
        if( this.name.equals(jug.name) )
            return false;
        if( this.score != jug.score )
            return false;
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
