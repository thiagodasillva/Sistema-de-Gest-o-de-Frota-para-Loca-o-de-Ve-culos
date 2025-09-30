package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs;

public class ClienteResponseDTO {

    private Long id;
    private String name;
    private String telefone;
    private String cpf;

    public ClienteResponseDTO(Long id, String name, String telefone, String cpf) {
        this.id = id;
        this.name = name;
        this.telefone = telefone;
        this.cpf = cpf;
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
}
