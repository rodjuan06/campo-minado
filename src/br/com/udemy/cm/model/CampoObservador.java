package br.com.udemy.cm.model;

@FunctionalInterface
public interface CampoObservador {
    public void eventoOcorreu(Campo campo, CampoEvento evento);
}
