package DTOs;

import jakarta.persistence.*;
import models.Veiculo;

import java.util.Set;

public class TipoVeiculoDTO {


    private Long id;

    private String name;

    private String descricao;

    private double precoDiario;

    private Set<Veiculo> veiculoSets;

    public TipoVeiculoDTO() {

    }

    public TipoVeiculoDTO(Long id, String name, String descricao, double precoDiario, Set<Veiculo> veiculoSets) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.precoDiario = precoDiario;
        this.veiculoSets = veiculoSets;
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

    public double getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(double precoDiario) {
        this.precoDiario = precoDiario;
    }

    public Set<Veiculo> getVeiculoSets() {
        return veiculoSets;
    }

    public void setVeiculoSets(Set<Veiculo> veiculoSets) {
        this.veiculoSets = veiculoSets;
    }
}
