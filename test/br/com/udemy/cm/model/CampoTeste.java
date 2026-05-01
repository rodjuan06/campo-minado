package br.com.udemy.cm.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CampoTeste {
    private Campo campo;

    @BeforeEach
    void iniciarCampo() {
        campo = new Campo(3, 3);
    }

    @Test
    void testeVizinhoRealDistanciaAbaixo() {
        Campo vizinho = new Campo(3, 2);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoRealDistanciaDiagonal() {
        Campo vizinho = new Campo(4, 4);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertTrue(resultado);
    }

    @Test
    void testeVizinhoFalso() {
        Campo vizinho = new Campo(1, 1);

        boolean resultado = campo.adicionarVizinho(vizinho);

        assertFalse(resultado);
    }

    @Test
    void testValorPadraoAlternarMarcacao() {
        assertFalse(campo.isMarcado());
    }

    @Test
    void testeAlternarMarcacao() {
        campo.alternarMarcacao();
        assertTrue(campo.isMarcado());
    }

    @Test
    void testAlternarMarcacaoDuasChamadas() {
        campo.alternarMarcacao();
        campo.alternarMarcacao();
        assertFalse(campo.isMarcado());
    }

    @Test
    void testAbrirNaoMinadoNaoMarcado() {

        assertTrue(campo.abrir());
    }

    @Test
    void testAbrirNaoMinadoMarcado() {
        campo.alternarMarcacao();

        assertFalse(campo.abrir());
    }

    @Test
    void testAbrirMinadoMarcado() {
        campo.alternarMarcacao();
        campo.minar();
        assertFalse(campo.abrir());
    }

    @Test
    void testAbrirMinadoNaoMarcado() {
        campo.minar();
    }

    @Test
    void abrirComVizinhos () {
        Campo vizinho1 = new Campo(2, 2);
        Campo vizinhoDoVizinho1 = new Campo(1, 1);

        vizinho1.adicionarVizinho(vizinhoDoVizinho1);

        campo.adicionarVizinho(vizinho1);

        campo.abrir();

        assertTrue(vizinho1.isAberto() && vizinhoDoVizinho1.isAberto());
    }

    @Test
    void abrirComVizinhos2 () {
        Campo vizinho1 = new Campo(2, 2);

        Campo vizinhoDoVizinho1 = new Campo(1, 1);
        Campo vizinhoDoVizinho2 = new Campo(0, 1);
        vizinhoDoVizinho2.minar();

        vizinho1.adicionarVizinho(vizinhoDoVizinho1);
        vizinho1.adicionarVizinho(vizinhoDoVizinho2);

        campo.adicionarVizinho(vizinho1);

        campo.abrir();

        assertTrue(vizinho1.isAberto() && !vizinhoDoVizinho1.isAberto());
    }
}
