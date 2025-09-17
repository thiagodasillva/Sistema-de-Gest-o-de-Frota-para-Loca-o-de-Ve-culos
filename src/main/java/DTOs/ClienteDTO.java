package DTOs;

import models.Cliente;

public class ClienteDTO {

    private Long id;
    private String name;
    private String telefone;
    private String cpf;
    private String senha;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String name, String telefone, String cpf, String senha) {
        this.id = id;
        this.name = name;
        this.telefone = telefone;
        this.cpf = cpf;
        this.senha = senha;
    }

    public ClienteDTO(Cliente cliente){
        this.id = cliente.getId();
        this.name = cliente.getName();
        this.telefone = cliente.getTelefone();
        this.cpf = cliente.getCpf();
        this.senha = cliente.getSenha();

    }
}
