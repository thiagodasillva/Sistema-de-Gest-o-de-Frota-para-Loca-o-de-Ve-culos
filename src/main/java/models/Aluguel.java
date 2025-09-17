package models;

import jakarta.persistence.*;

import javax.xml.crypto.Data;
import java.time.LocalDate;

@Entity
@Table(name = "tb_aluguel")
public class Aluguel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inicio")
    private LocalDate dataInicio;

    @Column(name = "fim")
    private LocalDate dataFim;

    @Column(name = "valor")
    private float valorTotal;

    @Column(name = "status")
    private String status;

    @Column(name = "texa")
    private float  taxaFixa;

    @Column(name = "veiculo")
    private Veiculo veiculo;

    @Column(name = "cliente")
    private Cliente cliente;


    public Aluguel() {
    }

    public Aluguel(Long id, LocalDate dataInicio, LocalDate dataFim, float valorTotal, String status, float taxaFixa, Veiculo veiculo, Cliente cliente) {
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.valorTotal = valorTotal;
        this.status = status;
        this.taxaFixa = taxaFixa;
        this.veiculo = veiculo;
        this.cliente = cliente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public float getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(float valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getTaxaFixa() {
        return taxaFixa;
    }

    public void setTaxaFixa(float taxaFixa) {
        this.taxaFixa = taxaFixa;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
