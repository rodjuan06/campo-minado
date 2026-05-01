package br.com.udemy.cm.view;

import br.com.udemy.cm.model.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() throws HeadlessException {
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 5);

        add(new PainelTabuleiro(tabuleiro));

        setTitle("Campo Minado");
        setSize(690, 438);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
