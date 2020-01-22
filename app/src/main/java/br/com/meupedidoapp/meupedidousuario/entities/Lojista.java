package br.com.meupedidoapp.meupedidousuario.entities;

public class Lojista extends Usuario {
    private String nomeFantasia;
    private String telefone3;
    //private Endereco endereco[];
    private boolean possuiLojaFisica;
    private String categoriaPrimaria;
    private float avaliacao;
    private int qtdeAvaliacao;
    private String imagemPerfil;
    private String marketingBanner;
    private boolean estaAberto;
    private Tema tema;
    private int tempoEntrega;
    private double taxaEntrega;
    private String perfilInstagram;
    private boolean pagamentoCartao;

    public Lojista(){}

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTelefone3() {
        return telefone3;
    }

    public void setTelefone3(String telefone3) {
        this.telefone3 = telefone3;
    }

    public boolean isPossuiLojaFisica() {
        return possuiLojaFisica;
    }

    public void setPossuiLojaFisica(boolean possuiLojaFisica) {
        this.possuiLojaFisica = possuiLojaFisica;
    }

    public String getCategoriaPrimaria() {
        return categoriaPrimaria;
    }

    public void setCategoriaPrimaria(String categoriaPrimaria) {
        this.categoriaPrimaria = categoriaPrimaria;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public int getQtdeAvaliacao() {
        return qtdeAvaliacao;
    }

    public void setQtdeAvaliacao(int qtdeAvaliacao) {
        this.qtdeAvaliacao = qtdeAvaliacao;
    }

    public String getImagemPerfil() {
        return imagemPerfil;
    }

    public void setImagemPerfil(String imagemPerfil) {
        this.imagemPerfil = imagemPerfil;
    }

    public String getMarketingBanner() {
        return marketingBanner;
    }

    public void setMarketingBanner(String marketingBanner) {
        this.marketingBanner = marketingBanner;
    }

    public boolean isEstaAberto() {
        return estaAberto;
    }

    public void setEstaAberto(boolean estaAberto) {
        this.estaAberto = estaAberto;
    }

    public Tema getTema() {
        return tema;
    }

    public void setTema(Tema tema) {
        this.tema = tema;
    }

    public int getTempoEntrega() {
        return tempoEntrega;
    }

    public void setTempoEntrega(int tempoEntrega) {
        this.tempoEntrega = tempoEntrega;
    }

    public double getTaxaEntrega() {
        return taxaEntrega;
    }

    public void setTaxaEntrega(double taxaEntrega) {
        this.taxaEntrega = taxaEntrega;
    }

    public String getPerfilInstagram() {
        return perfilInstagram;
    }

    public void setPerfilInstagram(String perfilInstagram) {
        this.perfilInstagram = perfilInstagram;
    }

    public boolean isPagamentoCartao() {
        return pagamentoCartao;
    }

    public void setPagamentoCartao(boolean pagamentoCartao) {
        this.pagamentoCartao = pagamentoCartao;
    }
}
