package br.com.udemy.cm.view;

import br.com.udemy.cm.model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton  implements CampoObservador, MouseListener {
    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);

    private Campo campo;


    public BotaoCampo(Campo campo) {
        this.campo = campo;

        setBorder(BorderFactory.createBevelBorder(0));
        setOpaque(true);
        setBackground(BG_PADRAO);

        addMouseListener(this);
        campo.registrarObservador(this);
    }

    @Override
    public void eventoOcorreu(Campo campo, CampoEvento evento) {
        switch (evento) {
            case ABRIR -> aplicarEstiloAbrir();
            case MARCAR -> aplicarEstiloMarcar();
            case EXPLODIR -> aplicarEstiloExplodir();
            default -> aplicarEstiloPadrao();
        }
    }

    private void aplicarEstiloAbrir() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (campo.isMinado()) {
            setBackground(BG_EXPLODIR);
            return;
        }

        switch (campo.minasNaVizinhanca()) {
            case 1 -> {
                setForeground(TEXTO_VERDE);
            }
            case 2 -> {
                setForeground(Color.BLUE);
            }
            case 3 -> {
                setForeground(Color.YELLOW);
            }
            case 4 -> {

            }
            case 5 -> {

            }
            case 6 -> {
                setForeground(Color.RED);
            }
            default -> {
                setForeground(Color.PINK);
            }
        }

        String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + "" : "";
        setText(valor);
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.WHITE);
        setText("X");
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.BLACK);
        setText("M");
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == 1) {
            campo.abrir();
        } else {
            campo.alternarMarcacao();
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}
