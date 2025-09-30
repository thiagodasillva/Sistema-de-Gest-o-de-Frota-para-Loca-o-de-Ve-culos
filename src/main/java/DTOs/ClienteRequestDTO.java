package DTOs;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public class ClienteRequestDTO {

    @NotBlank(message = "O nome é obrigatório")
    @Size(min = 3)
    private String name;

    @NotBlank(message = "O telefone é obrigatório")
    @Size(min = 8, max = 100)
    private String telefone;

    @NotBlank(message = "O CPF é obrigatório")
    @CPF
    private String cpf;

    public ClienteRequestDTO(String name, String telefone, String cpf) {
        this.name = name;
        this.telefone = telefone;
        this.cpf = cpf;
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
