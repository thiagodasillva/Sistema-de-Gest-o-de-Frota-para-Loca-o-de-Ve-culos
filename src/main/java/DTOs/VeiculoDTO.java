package DTOs;

import models.TipoVeiculo;

import javax.xml.crypto.Data;

public class VeiculoDTO {

    private Long id;
    private String placa;
    private String modelo;
    private String marca;
    private Data ano;
    private String cor;
    private boolean disponivel;
    private TipoVeiculo tipoVeiculo;

    public VeiculoDTO() {

    }

    public VeiculoDTO(Long id, String placa, String modelo, String marca, Data ano, String cor, boolean disponivel, TipoVeiculo tipoVeiculo) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
        this.disponivel = disponivel;
        this.tipoVeiculo = tipoVeiculo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Data getAno() {
        return ano;
    }

    public void setAno(Data ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }
}
