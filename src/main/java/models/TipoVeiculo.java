package models;

public class TipoVeiculo {

    private Long id;
    private String name;
    private String descricao;
    private double precoDiario;

    public TipoVeiculo() {
    }

    public TipoVeiculo(Long id, String name, String descricao, double precoDiario) {
        this.id = id;
        this.name = name;
        this.descricao = descricao;
        this.precoDiario = precoDiario;
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
}
