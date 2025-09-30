package DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import models.TipoVeiculo;
import models.VeiculoStatus;

public class VeiculoRequestDTO {

    @NotBlank(message = "O campo placa não pode ser deixada vazia")
    @Size(min = 3,max = 20, message = "A placa deve ter entre 3 e 20 caracteris")
    private String placa;

    @NotBlank(message = "O campo modelo não pode ser deixada vazia")
    @Size(min = 3,max = 200, message = "O modelo deve ter entre 3 e 200 caracteris")
    private String modelo;

    @NotBlank(message = "O campo Marca não pode ser deixada vazia")
    @Size(min = 3,max = 20, message = "A marca deve ter entre 3 e 20 caracteris")
    private String marca;

    @NotBlank(message = "O campo ano não pode ser deixada vazia")
    @Size(max = 20, message = "A placa deve ter 4 caracteris")
    private Integer ano;

    @NotBlank(message = "O campo cor não pode ser deixada vazia")
    @Size(min = 3,max = 20, message = "A placa deve ter entre 3 e 20 caracteris")
    private String cor;

    @NotBlank(message = "O campo placa não pode ser deixada vazia")
    @Size(min = 3,max = 20, message = "A placa deve ter entre 3 e 20 caracteris")

    @NotBlank(message = "O Veiculo deve ter um tipo definido")
    private Long tipoVeiculoID;
    private VeiculoStatus status = VeiculoStatus.DISPONIVEL;


    public VeiculoRequestDTO(String placa, String modelo, String marca, Integer ano, String cor, Long tipoVeiculoID, VeiculoStatus status) {
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
        this.tipoVeiculoID = tipoVeiculoID;
        this.status = status;
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

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public Long getTipoVeiculoID() {
        return tipoVeiculoID;
    }

    public void setTipoVeiculoID(Long tipoVeiculoID) {
        this.tipoVeiculoID = tipoVeiculoID;
    }

    public VeiculoStatus getStatus() {
        return status;
    }

    public void setStatus(VeiculoStatus status) {
        this.status = status;
    }
}
