package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class TipoVeiculoRequestDTO {

    @NotBlank(message = "O nome do tipo de veiculo não pode ser deixado em branco")
    @Size(min = 3, max = 1000 , message = "O nome deve ter entre 3 e 100 caracteris")
    private String name;

    @NotBlank(message = "Deve ser fornecida uma descrição com as caracteristicas do tipo do veiculo")
    @Size(min = 5, max = 500, message = "A descrição pode ter entre 5 a 500 caracteris")
    private String descricao;

    @NotNull(message = "O valor do preço diario referente ao tipo do veiculo não pode ser deixado em branco")
    @Positive(message = "O preço diário deve ser um valor positivo.")
    private BigDecimal precoDiario;

    public TipoVeiculoRequestDTO(String name, String descricao, BigDecimal precoDiario) {
        this.name = name;
        this.descricao = descricao;
        this.precoDiario = precoDiario;
    }

    public TipoVeiculoRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(BigDecimal precoDiario) {
        this.precoDiario = precoDiario;
    }
}
