
public class Cronometro {

    private long valorInicio;
    private long valorParada;
    private long tempoDiferenca;

    public void inicio() {
        valorInicio = System.currentTimeMillis();
        valorParada = 0;
        tempoDiferenca = 0;
    }

    public void parada() {
        valorParada = System.currentTimeMillis();
        tempoDiferenca = valorParada - valorInicio;
    }

    public long tempoGasto() {
        return tempoDiferenca;
    }
}
