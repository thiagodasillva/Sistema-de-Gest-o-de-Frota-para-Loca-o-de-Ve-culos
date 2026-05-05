package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception;

public class VeiculoStatusError extends RuntimeException{

    public VeiculoStatusError(String mensagem){
        super(mensagem);
    }
}
