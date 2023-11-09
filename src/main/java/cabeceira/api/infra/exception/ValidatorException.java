package cabeceira.api.infra.exception;

public class ValidatorException extends RuntimeException {
    public ValidatorException(String message) {
        super(message);
    }
}