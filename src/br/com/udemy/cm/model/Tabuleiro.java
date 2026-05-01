package br.com.udemy.cm.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Tabuleiro implements CampoObservador {
    private final int linhas;
    private final int colunas;
    private final int minas;

    private final List<Campo> campos = new ArrayList<>();
    private final List<Consumer<ResultadoEvento>> observadores = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    public void paraCadaCampo(Consumer<Campo> funcao) {
        campos.forEach(funcao);
    }

    public void registrarObservador(Consumer<ResultadoEvento> observador) {
        observadores.add(observador);
    }

    public void notificarObservador(boolean resultado) {
        observadores.forEach(o -> o.accept(new ResultadoEvento(resultado)));
    }

    public void abrir(int linha, int coluna) {
        campos.parallelStream().filter(c -> c.getY() == linha && c.getX() == coluna).findFirst().ifPresent(Campo::abrir);
    }

    private void mostrarMinas() {
        campos.stream().filter(c -> c.isMinado() && !c.isMarcado()).forEach(c -> c.setAberto(true));
    }

    public void alternarMarcacao(int linha, int coluna) {
        campos.parallelStream().filter(c -> c.getY() == linha && c.getX() == coluna).findFirst().ifPresent(Campo::alternarMarcacao);
    }

    private void gerarCampos() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                Campo campo = new Campo(i, j);
                campo.registrarObservador(this);
                campos.add(campo);
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1 : campos) {
            for (Campo c2 : campos) {
                c1.adicionarVizinho(c2);
            }
        }
    }

    private void sortearMinas() {
        for (int i = 0; i < minas; i++) {
            int numero = (int) (Math.random() * campos.size());
            Campo campo = campos.get(numero);

            if (!campo.isMinado()) campo.minar();
            else {
                i--;
            }
        }
    }

    public boolean objetivoAlcancado() {
        return campos.stream().allMatch(Campo::objetivoAlcancado);
    }

    public void reiniciar() {
        campos.forEach(Campo::reiniciar);
        sortearMinas();
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        if (evento == CampoEvento.EXPLODIR) {
            mostrarMinas();
            notificarObservador(false);
        } else if (objetivoAlcancado()) {
            notificarObservador(true);
        }
    }

    public int getMinas() {
        return minas;
    }

    public int getColunas() {
        return colunas;
    }

    public int getLinhas() {
        return linhas;
    }
}
