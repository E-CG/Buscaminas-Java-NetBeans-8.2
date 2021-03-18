/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Esteban Cossio Gonzalez
 */
public class Casilla {
    private int posFila;
    private int posColumna;
    private boolean mina;
    private int nroMinasAlrededor;
    private boolean abierta;

    public Casilla(int posFila, int posColumna) {
        this.posFila = posFila;
        this.posColumna = posColumna;
    }
    
    public int getPosFila() {
        return posFila;
    }

    public void setPosFila(int posFila) {
        this.posFila = posFila;
    }

    public int getPosColumna() {
        return posColumna;
    }

    public void setPosColumna(int posColumna) {
        this.posColumna = posColumna;
    }

    public boolean esMina() {
        return mina;
    }

    public void setMina(boolean mina) {
        this.mina = mina;
    }

    public int getNroMinasAlrededor() {
        return nroMinasAlrededor;
    }

    public void setNroMinasAlrededor(int nroMinasAlrededor) {
        this.nroMinasAlrededor = nroMinasAlrededor;
    }
    
    public void incrementarNroMinasAlrededor(){
     this.nroMinasAlrededor++;   
    }

    public boolean esAbierta() {
        return abierta;
    }

    public void setAbierta(boolean abierta) {
        this.abierta = abierta;
    }   
    
}
