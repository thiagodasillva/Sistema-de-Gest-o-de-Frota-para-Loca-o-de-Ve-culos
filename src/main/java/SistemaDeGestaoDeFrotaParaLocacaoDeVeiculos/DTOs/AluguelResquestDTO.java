package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class AluguelResquestDTO {

    @NotBlank(message = "O ID do cliente é obrigatório")
    private Long clienteId;

    @NotBlank(message = "O ID do veículo é obrigatório")
    private Long veiculoId;

    @NotBlank(message = "a taxa fixa não pode ser vazia")
    private BigDecimal taxaFixa;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dataInicio;

    public AluguelResquestDTO() {

    }

    public AluguelResquestDTO(Long clienteId, Long veiculoId, BigDecimal taxaFixa, LocalDate TimedataInicio) {
        this.clienteId = clienteId;
        this.veiculoId = veiculoId;
        this.taxaFixa = taxaFixa;
        this.dataInicio = dataInicio;
    }

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getVeiculoId() {
        return veiculoId;
    }

    public void setVeiculoId(Long veiculoId) {
        this.veiculoId = veiculoId;
    }

    public BigDecimal getTaxaFixa() {
        return taxaFixa;
    }

    public void setTaxaFixa(BigDecimal taxaFixa) {
        this.taxaFixa = taxaFixa;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }
}

