/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package linuxmemo.Control;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Vector;
import java.sql.SQLException;
import linuxmemo.Modelo.Jugador;

public class Scores
{
    protected String con;
    protected Connection cn;
    protected Statement stm;
    protected ResultSet rs;
    
    private Connection getConnection()
    {
        con = "jdbc:mysql://localhost/puntajes?" + "user=root";
        this.cn = null;
        
        try
        {
            //Class.forName(this.con);
            this.cn = DriverManager.getConnection(this.con);
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage()); 
            System.out.println("SQLState: " + e.getSQLState()); 
            System.out.println("VendorError: " + e.getErrorCode());
        }
        return this.cn;
    }
    
     public void saveJug( Jugador jug ) 
     {
        this.cn = null;
        boolean res;
        
        try
        {
            this.cn = this.getConnection();
            this.stm = this.cn.createStatement();
            res = this.stm.execute( "insert into puntajes (jugador, puntaje) values( \'"+ jug.getName() +"\',"+ jug.getScore() +")" );
            if (res) 
            {
                rs = stm.getResultSet();
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage()); 
            System.out.println("SQLState: " + e.getSQLState()); 
            System.out.println("VendorError: " + e.getErrorCode());
        }
        finally 
        {
            if (rs != null) 
            {
                try 
                { 
                    rs.close();
                } 
                catch (SQLException ex) 
                { 
                } // ignore rs y stm = null;
                rs = null;
            }
            if (stm != null) 
            {
                try 
                { 
                    stm.close();
                } 
                catch (SQLException ex) 
                { 
                } // ignore rs y stm = null;
                stm = null;
            }
        }
    }
    
    public Vector getDataVector() 
    {
        Vector puntajes = null;
        this.cn = null;
        
        try
        {
            puntajes = new Vector();
            this.cn = this.getConnection();
            this.stm = this.cn.createStatement();
            this.rs = this.stm.executeQuery( "select jugador,puntaje from puntajes" );
 
            while( rs.next() ) 
            {
                Vector aux = new Vector();
                aux.addElement( this.rs.getString("jugador"));
                aux.addElement( this.rs.getString("puntaje"));
                puntajes.add(aux);
            }   
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage()); 
            System.out.println("SQLState: " + e.getSQLState()); 
            System.out.println("VendorError: " + e.getErrorCode());
        }
        finally 
        {
            if (rs != null) 
            {
                try 
                { 
                    rs.close();
                } 
                catch (SQLException ex) 
                { 
                } // ignore rs y stm = null;
                rs = null;
            }
            if (stm != null) 
            {
                try 
                { 
                    stm.close();
                } 
                catch (SQLException ex) 
                { 
                } // ignore rs y stm = null;
                stm = null;
            }
        }
        return puntajes;
    }
    
    public Vector getNamesVector() 
    {
        Vector nombres = new Vector();
        nombres.add("jugador");
        nombres.add("puntaje");
        
        return nombres;
    }
}
