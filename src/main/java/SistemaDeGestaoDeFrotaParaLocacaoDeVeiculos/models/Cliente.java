package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "tb_cliente")
public class Cliente implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "tel")
    private String telefone;

    @Column(name = "cpf", unique = true)
    private String cpf;

    @Column(name = "ativo")
    private boolean ativo = true;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    private Set<Aluguel> alugueis = new java.util.HashSet<>();


    @Column(name = "password", nullable = false)
    private String password;

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

    public Set<Aluguel> getAlugueis() {
        return alugueis;
    }

    public void setAlugueis(Set<Aluguel> alugueis) {
        this.alugueis = alugueis;
    }

    public void setPassword(String password) {
        this.password = password;
    }






    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    // o CPF vai ser o userName para o cliente
    @Override
    public String getUsername() {
        return this.cpf;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Use o seu campo de exclusão lógica!
        return this.ativo;
    }

    public String getSenha() { return password; }
    public void setSenha(String senha) { this.password = senha; }




















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
