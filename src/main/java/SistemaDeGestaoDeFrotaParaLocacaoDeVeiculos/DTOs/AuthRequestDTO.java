package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.DTOs;

public class AuthRequestDTO {

    private String cpf;
    private String password;


    public AuthRequestDTO() {

    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
