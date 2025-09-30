package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_tipo")
public class TipoVeiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "diaria")
    private BigDecimal precoDiario;

    @OneToMany(mappedBy = "tipoVeiculo", fetch = FetchType.LAZY)
    private Set<Veiculo> veiculoSets;

    public TipoVeiculo() {
    }

    public TipoVeiculo(Long id, String name, String descricao, BigDecimal precoDiario) {
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

    public BigDecimal getPrecoDiario() {
        return precoDiario;
    }

    public void setPrecoDiario(BigDecimal precoDiario) {
        this.precoDiario = precoDiario;
    }

    public Set<Veiculo> getVeiculoSets() {
        return veiculoSets;
    }

    public void setVeiculoSets(Set<Veiculo> veiculoSets) {
        this.veiculoSets = veiculoSets;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipoVeiculo that = (TipoVeiculo) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
