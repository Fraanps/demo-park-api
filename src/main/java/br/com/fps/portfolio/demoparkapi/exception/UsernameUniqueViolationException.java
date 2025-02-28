package br.com.fps.portfolio.demoparkapi.exception;

public class UsernameUniqueViolationException extends RuntimeException{
  public UsernameUniqueViolationException(String message) {
    super(message);
  }

}
