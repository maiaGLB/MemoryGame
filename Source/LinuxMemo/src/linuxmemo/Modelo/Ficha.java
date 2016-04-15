
package linuxmemo.Modelo;

public class Ficha 
{
    protected String img = "img.png";
    protected int nro = 0;  // nro de par                        
    protected boolean estado = false; // false=bocaAbajo  true=bocaArriba

    public Ficha( String new_img, int new_nro, boolean new_estado ) 
    {
        this.img = new_img;
        this.nro = new_nro;
        this.estado = new_estado;
    }
    
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getNro() {
        return nro;
    }

    public void setNro(int nro) {
        this.nro = nro;
    }
    
}
