package com.furb.controle.model;

public class MarcaDTO {
    private int MARCA_ID;

    private String razaoSocial;

    private String nome;

    private String cnpj;

    public MarcaDTO(int MARCA_ID, String razaoSocial, String nome, String cnpj) {
        this.setMARCA_ID(MARCA_ID);
        this.setRazaoSocial(razaoSocial);
        this.setNome(nome);
        this.setCnpj(cnpj);
    }

    public long getMARCA_ID() {
        return MARCA_ID;
    }

    public void setMARCA_ID(int MARCA_ID) {
        this.MARCA_ID = MARCA_ID;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
}
