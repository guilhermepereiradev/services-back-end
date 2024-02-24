package com.soulcode.servicos.model;

import jakarta.persistence.*;
import org.springframework.format.annotation.NumberFormat;

@Entity
public class Pagamento {

    @Id
    private Integer id;

    @NumberFormat(pattern = "#.##0,00")
    @Column(nullable = false)
    private double valor;

    @Column(nullable = false)
    private String formPagamento;

    @Enumerated(EnumType.STRING)
    private StatusPagamento status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getFormPagamento() {
        return formPagamento;
    }

    public void setFormPagamento(String formPagamento) {
        this.formPagamento = formPagamento;
    }

    public StatusPagamento getStatus() {
        return status;
    }

    public void setStatus(StatusPagamento status) {
        this.status = status;
    }
}
