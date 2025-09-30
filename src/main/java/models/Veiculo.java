package models;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_veiculo")
public class Veiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "placa")
    private String placa;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "marca")
    private String marca;

    @Column(name = "ano")
    private Integer ano;

    @Column(name = "cor")
    private String cor;

    @Column(name = "status")
    private VeiculoStatus status = VeiculoStatus.DISPONIVEL;

    @ManyToOne
    @JoinColumn(name = "tipoVeiculo")
    private TipoVeiculo tipoVeiculo;

    @OneToMany(mappedBy = "veiculo", fetch = FetchType.LAZY)
    private Set<Aluguel> alugueis = new java.util.HashSet<>();



    public Veiculo() {
    }

    public Veiculo(Long id, String placa, String modelo, String marca, Integer ano, String cor, VeiculoStatus status, TipoVeiculo tipoVeiculo, Set<Aluguel> alugueis) {
        this.id = id;
        this.placa = placa;
        this.modelo = modelo;
        this.marca = marca;
        this.ano = ano;
        this.cor = cor;
        this.status = status;
        this.tipoVeiculo = tipoVeiculo;
        this.alugueis = alugueis;
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

    public TipoVeiculo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(TipoVeiculo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public VeiculoStatus getStatus() {
        return status;
    }

    public void setStatus(VeiculoStatus status) {
        this.status = status;
    }

    public Set<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(Set<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Veiculo veiculo = (Veiculo) o;
        return Objects.equals(id, veiculo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
