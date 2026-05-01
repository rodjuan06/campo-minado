package br.com.udemy.cm.model;

import java.util.ArrayList;
import java.util.List;

public class Campo {
    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private final int x;
    private final int y;

    private List<Campo> vizinhos = new ArrayList<>();
    private List<CampoObservador> observadores = new ArrayList<>();


    Campo(int x, int y) {
        this.minado = false;
        this.aberto = false;
        this.marcado = false;

        this.x = x;
        this.y = y;
    }

    public void registrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }

    public void notificarObservador(CampoEvento evento) {
        observadores.forEach(o -> o.eventoOcorreu(this, evento));
    }

    boolean adicionarVizinho(Campo c) {
        if (Math.abs(this.x - c.x) + Math.abs(this.y - c.y) == 1 || (Math.abs(this.x - c.x) == 1 && Math.abs(this.y - c.y) == 1)) {
            this.vizinhos.add(c);
            return true;
        }
        return false;
    }

    public void alternarMarcacao() {

        if (aberto) return;
        this.marcado = !this.marcado;

        if (marcado) notificarObservador(CampoEvento.MARCAR);
        else notificarObservador(CampoEvento.DESMARCAR);
    }

    public boolean abrir() {
        if (aberto || marcado) return false;

        setAberto(true);

        if (minado) {
            notificarObservador(CampoEvento.EXPLODIR);
            return true;
        }

        notificarObservador(CampoEvento.ABRIR);

        if (vizinhancaSegura()) {
            vizinhos.forEach(Campo::abrir);
        }

        return true;
    }

    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(c -> c.minado);
    }

    public boolean isMarcado() {
        return marcado;
    }

    public boolean isMinado() {
        return minado;
    }

    boolean minar() {
        if (minado) return false;

        minado = true;
        return true;
    }

    public boolean isAberto() {
        return aberto;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;

        if (aberto) {
            notificarObservador(CampoEvento.ABRIR);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;

        return desvendado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) vizinhos.stream().filter((v -> v.minado)).count();
    }

    void reiniciar() {
        this.minado = false;
        this.aberto = false;
        this.marcado = false;
        notificarObservador(CampoEvento.REINICIAR);
    }
}
