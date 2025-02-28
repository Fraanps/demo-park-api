package br.com.fps.portfolio.demoparkapi.exception;

public class EntityNotFoundException extends RuntimeException{
  public EntityNotFoundException(String message) {
    super(message);
  }
}
