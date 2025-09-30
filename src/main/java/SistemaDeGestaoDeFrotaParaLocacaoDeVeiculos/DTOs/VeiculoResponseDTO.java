package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs;


import SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models.VeiculoStatus;

public class VeiculoResponseDTO {

    private Long id;
    private String placa;
    private String modelo;
    private String marca;
    private VeiculoStatus status;
    private Long tipoVeiculoId;


    public VeiculoResponseDTO(Long id, String placa, String modelo, String marca, VeiculoStatus status, Long tipoVeiculoId) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.status = status;
        this.tipoVeiculoId = tipoVeiculoId;
    }

    public VeiculoResponseDTO() {

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

    public VeiculoStatus getStatus() {
        return status;
    }

    public void setStatus(VeiculoStatus status) {
        this.status = status;
    }

    public Long getTipoVeiculoId() {
        return tipoVeiculoId;
    }

    public void setTipoVeiculoId(Long tipoVeiculoId) {
        this.tipoVeiculoId = tipoVeiculoId;
    }
}




