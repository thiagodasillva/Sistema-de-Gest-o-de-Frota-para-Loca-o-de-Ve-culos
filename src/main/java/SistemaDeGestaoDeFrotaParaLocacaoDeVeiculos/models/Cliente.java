package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tel")
    private String telefone;

    @Column(name = "cpf")
    private String cpf;

    @Column(name = "ativo")
    private boolean ativo = true;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Aluguel> alugueis = new java.util.HashSet<>();


    //private String senha;

    public Cliente() {
    }

    public Cliente(Long id, String name, String telefone, String cpf, Set<Aluguel> alugueis) {
        this.id = id;
        this.name = name;
        this.telefone = telefone;
        this.cpf = cpf;
        this.alugueis = alugueis;
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

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Set<Aluguel> getAlgueis() {
        return alugueis;
    }

    public void setAlgueis(Set<Aluguel> algueis) {
        this.alugueis = algueis;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
    /*public String getSenha() {
        return senha;
    }

    //public void setSenha(String senha) {
        this.senha = senha;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cliente cliente = (Cliente) o;
        return Objects.equals(id, cliente.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
