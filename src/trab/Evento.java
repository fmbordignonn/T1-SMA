package trab;

public class Evento {
    TipoEvento evento;
    double tempo;

    public Evento(TipoEvento evento, double tempo) {
        this.evento = evento;
        this.tempo = tempo;
    }

    public TipoEvento getEvento() {
        return evento;
    }

    public double getTempo() {
        return tempo;
    }
}
