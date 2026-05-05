package SistemaDeGestaoDeFrotaParaLocacaoDeVeiculos.exception;

public class DateConflictException extends RuntimeException {

    public DateConflictException(String mensagem){
        super (mensagem);
    }
}
