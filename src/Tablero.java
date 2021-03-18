
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author Esteban Cossio Gonzalez
 */
public class Tablero {

    Casilla[][] casillas;

    int nroFilas;
    int nroColumnas;
    int nroMinas;

    int nroCasillasAbiertas;
    boolean juegoTerminado;

    private Consumer<List<Casilla>> eventoPartidaPerdida; //Cuando se pierda la partida se va a notificar cuales son las casillas con minas
    private Consumer<Casilla> eventoCasillaAbierta; //Para saber que casilla se está abriendo
    private Consumer<List<Casilla>> eventoPartidaGanada; //Cuando se gane una partida salta este evento 

    public static void main(String[] args) {
        Tablero tablero = new Tablero(5, 5, 5);
        tablero.mostrarTablero();
        System.out.println("-----");
        tablero.mostrarPistas();
    }

    public Tablero(int nroFilas, int nroColumnas, int nroMinas) {
        this.nroFilas = nroFilas;
        this.nroColumnas = nroColumnas;
        this.nroMinas = nroMinas;
        this.crearCasillas();
    }

    public void crearCasillas() {
        casillas = new Casilla[this.nroFilas][this.nroColumnas];
        for (int i = 0; i < casillas.length; i++) { //recorre las casillas por filas
            for (int j = 0; j < casillas[i].length; j++) {//recorre las casillas por columnas
                casillas[i][j] = new Casilla(i, j);
            }
        }
        crearMinas();
    }

    private void crearMinas() {
        int minasCreadas = 0;
        while (minasCreadas != nroMinas) {
            int posTempFila = (int) (Math.random() * casillas.length);
            int postTempColumna = (int) (Math.random() * casillas[0].length);
            if (!casillas[posTempFila][postTempColumna].esMina()) { //Si esa casilla es mina ya es mina la rechaza de lo contrario no.
                casillas[posTempFila][postTempColumna].setMina(true);
                minasCreadas++;
            }
        }
        actualizarNroMinas();
    }

    public void mostrarTablero() {
        for (int i = 0; i < casillas.length; i++) { //recorre las casillas por filas
            for (int j = 0; j < casillas[i].length; j++) {//recorre las casillas por columnas
                System.out.print(casillas[i][j].esMina() ? "*" : "0");
            }
            System.out.println("");
        }
    }

    public void mostrarPistas() {
        for (int i = 0; i < casillas.length; i++) { //recorre las casillas por filas
            for (int j = 0; j < casillas[i].length; j++) {//recorre las casillas por columnas
                System.out.print(casillas[i][j].getNroMinasAlrededor());
            }
            System.out.println("");
        }
    }

    private void actualizarNroMinas() {
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].esMina()) {
                    List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(i, j);
                    casillasAlrededor.forEach((c) -> c.incrementarNroMinasAlrededor()); //Función en la cual cada una de las casillas se le aplica el método de incrementar
                }
            }
        }
    }

    private List<Casilla> obtenerCasillasAlrededor(int posFila, int posColumna) {
        List<Casilla> listaCasillas = new LinkedList<>();
        for (int i = 0; i < 8; i++) {
            int tmpPosFila = posFila;
            int tmpPosColumna = posColumna;
            switch (i) {
                case 0: //Arriba
                    tmpPosFila--;
                    break;
                case 1: //Arriba Derecha
                    tmpPosFila--;
                    tmpPosColumna++;
                    break;
                case 2: //Derecha
                    tmpPosColumna++;
                    break;
                case 3: //Derecha Abajo
                    tmpPosColumna++;
                    tmpPosFila++;
                    break;
                case 4: //Abajo
                    tmpPosFila++;
                    break;
                case 5: //Abajo Izquierda
                    tmpPosFila++;
                    tmpPosColumna--;
                    break;
                case 6: //Izquierda
                    tmpPosColumna--;
                    break;
                case 7: //Izquierda Arriba
                    tmpPosFila--;
                    tmpPosColumna--;
                    break;
            }
            if (tmpPosFila >= 0 && tmpPosFila < this.casillas.length
                    && tmpPosColumna >= 0 && tmpPosColumna < this.casillas[0].length) {
                listaCasillas.add(this.casillas[tmpPosFila][tmpPosColumna]);
            }
        }
        return listaCasillas;
    }

    public void seleccionarCasilla(int posFila, int posColumna) {//Método que permite seleccionar la casilla presionando los botones
        //Cuando se abra o se seleccione una casilla se le dice al evento
        eventoCasillaAbierta.accept(this.casillas[posFila][posColumna]);
        if (this.casillas[posFila][posColumna].esMina()) {
            eventoPartidaPerdida.accept(obtenerCasillasMinadas());
        } else if (this.casillas[posFila][posColumna].getNroMinasAlrededor() == 0) { //Si hay casillas con pista 0 las revela
            //Además aquí se tiene que marcar que la casilla ha sido seleccionada
            marcarCasillaAbierta(posFila, posColumna);
            List<Casilla> casillasAlrededor = obtenerCasillasAlrededor(posFila, posColumna);
            for (Casilla casilla : casillasAlrededor) {
                if (!casilla.esAbierta()) {//Si la casilla no está abierta
                    seleccionarCasilla(casilla.getPosFila(), casilla.getPosColumna());//Método recursivo que permite no seguir abriendo las casillas
                }
            }
        } else {//Cuando se seleccione una mina que tenga minas alrededor hay que marcarla como abierta
            marcarCasillaAbierta(posFila, posColumna);
        }
        if (ganarPartida()) {
            eventoPartidaGanada.accept(obtenerCasillasMinadas());
        }
    }

    List<Casilla> obtenerCasillasMinadas() {
        //Cuando en la posicion recibida existe una mina
        List<Casilla> casillasMinadas = new LinkedList<>();
        for (int i = 0; i < casillas.length; i++) {
            for (int j = 0; j < casillas[i].length; j++) {
                if (casillas[i][j].esMina()) {
                    casillasMinadas.add(casillas[i][j]);
                }
            }
        }
        return casillasMinadas;
    }

    void marcarCasillaAbierta(int posFila, int posColumna) {//Este método se invoca cada vez que se seleccione una casilla 
        //Si la posicion de la casilla no ha sido abierta entonces...
        if (!this.casillas[posFila][posColumna].esAbierta()) {
            nroCasillasAbiertas++;
            this.casillas[posFila][posColumna].setAbierta(true);//Se marca que la casilla recibida ya está abierta
        }
    }

    boolean ganarPartida() {
        return nroCasillasAbiertas >= nroFilas * nroColumnas - nroMinas; //Si esto es verdad quiere decir que la partida ha sido ganada
    }

    public void setEventoPartidaPerdida(Consumer<List<Casilla>> eventoPartidaPerdida) {
        this.eventoPartidaPerdida = eventoPartidaPerdida;
    }

    public void setEventoCasillaAbierta(Consumer<Casilla> eventoCasillaAbierta) {
        this.eventoCasillaAbierta = eventoCasillaAbierta;
    }

    public void setEventoPartidaGanada(Consumer<List<Casilla>> eventoPartidaGanada) {
        this.eventoPartidaGanada = eventoPartidaGanada;
    }

}
