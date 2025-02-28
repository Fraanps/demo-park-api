package br.com.fps.portfolio.demoparkapi.web.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UsuarioCreateDto {


  //@NotNull // não aceita campos nulos
  //@NotEmpty // valida not null e se o campo está vazio, não passa
  @NotBlank // não aceita valor nulo e que contenha espaços em branco
  @Email(message = "formato do e-mail inválido!", regexp = "^[a-zA-Z0-9.+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,}$")
  private String username;

  @NotBlank
  @Size(min = 6, max = 6)
  private String password;

}
