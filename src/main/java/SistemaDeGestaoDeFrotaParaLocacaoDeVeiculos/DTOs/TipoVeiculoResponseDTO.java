package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs;

import java.math.BigDecimal;

public class TipoVeiculoResponseDTO {

    private Long id;
    private String name;
    private String descricao;
    private BigDecimal precoDiario;


    public TipoVeiculoResponseDTO(Long id, String name, String descricao, BigDecimal precoDiario) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.precoDiario = precoDiario;
    }

    public TipoVeiculoResponseDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
