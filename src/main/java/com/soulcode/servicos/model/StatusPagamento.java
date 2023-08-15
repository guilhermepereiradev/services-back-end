package com.soulcode.servicos.model;

public enum StatusPagamento {

    LANCADO("Lancado"),
    QUITADO("Quitado");

    private String conteudo;

    private StatusPagamento(String conteudo){
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }
}
