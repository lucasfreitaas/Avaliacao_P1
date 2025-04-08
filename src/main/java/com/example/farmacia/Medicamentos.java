package com.example.farmacia;

import java.time.LocalDate;

public class Medicamentos {
    private String codigoOriginal;
    private String nome;
    private String descricao;
    private String principioAtivo;
    private LocalDate dataValidade;
    private int qtdEstoque;
    private double preco;
    private boolean controlado;
    private Fornecedores fornecedores;
    private String strControlado;

    public Medicamentos(String codigoOriginal, String nome, String descricao, String principioAtivo, LocalDate dataValidade, int qtdEstoque,
                        double preco, boolean controlado, Fornecedores fornecedores) {
        this.codigoOriginal = codigoOriginal;
        this.nome = nome;
        this.descricao = descricao;
        this.principioAtivo = principioAtivo;
        this.dataValidade = dataValidade;
        this.qtdEstoque = qtdEstoque;
        this.preco = preco;
        this.controlado = controlado;
        this.fornecedores = fornecedores;
    }

    public Medicamentos() {
    }

    public String getCodigo_original() {
        return codigoOriginal;
    }

    public void setcodigoOriginal(String codigoOriginal) {
        this.codigoOriginal = codigoOriginal;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPrincipioAtivo() {
        return principioAtivo;
    }

    public void setPrincipioAtivo(String principioAtivo) {
        this.principioAtivo = principioAtivo;
    }

    public LocalDate getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(LocalDate dataValidade) {
        this.dataValidade = dataValidade;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public void setQtdEstoque(int qtdEstoque) {
        this.qtdEstoque = qtdEstoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public boolean isControlado() {
        return controlado;
    }

    public void setControlado(boolean controlado) {
        this.controlado = controlado;
    }

    public Fornecedores getFornecedores() {
        return fornecedores;
    }

    public void setFornecedores(Fornecedores fornecedores) {
        this.fornecedores = fornecedores;
    }

    public void converterControlado(){

        if (isControlado()){
            strControlado = "Sim";
        } else {
            strControlado = "Não";
        }
    }

    @Override
    public String toString() {
        return "Medicamentos{" +
                "codigo_original='" + codigoOriginal + '\'' +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", principioAtivo='" + principioAtivo + '\'' +
                ", dataValidade=" + dataValidade +
                ", qtdEstoque=" + qtdEstoque +
                ", preco=" + preco +
                ", controlado=" + strControlado +
                ", fornecedores=" + fornecedores +
                '}';
    }
}
