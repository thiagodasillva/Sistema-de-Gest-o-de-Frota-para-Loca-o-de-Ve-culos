package DTOs;

import models.AluguelStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AluguelResponseDTO {

    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private BigDecimal valorTotal;
    private AluguelStatus status;

    // Em vez dos objetos completos, usamos IDs e nomes
    private Long clienteId;
    private String clienteNome;
    private Long veiculoId;
    private String veiculoPlaca;


    public AluguelResponseDTO(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, BigDecimal valorTotal, AluguelStatus status, Long clienteId, String clienteNome, Long veiculoId, String veiculoPlaca) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
        this.clienteId = clienteId;
        this.clienteNome = clienteNome;
        this.veiculoId = veiculoId;
        this.veiculoPlaca = veiculoPlaca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public AluguelStatus getStatus() {
        return status;
    }

    public void setStatus(AluguelStatus status) {
        this.status = status;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public String getClienteNome() {
        return clienteNome;
    }

    public void setClienteNome(String clienteNome) {
        this.clienteNome = clienteNome;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public String getVeiculoPlaca() {
        return veiculoPlaca;
    }

    public void setVeiculoPlaca(String veiculoPlaca) {
        this.veiculoPlaca = veiculoPlaca;
    }
}
