package br.com.fps.portfolio.demoparkapi;

// testes de ponta a ponta - integração

import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioCreateDto;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioResponseDto;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioSenhaDto;
import br.com.fps.portfolio.demoparkapi.web.exceptions.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // servidor(tomcat) em ambiente de teste
@Sql(scripts = "classpath:/usuarios/sql/usuarios-insert.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)// importação dos scrips sql
@Sql(scripts = "classpath:/usuarios/sql/usuarios-delete.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)// importação dos scrips sql
@ActiveProfiles("test")
public class UsuarioApiTest {

  @Autowired
  WebTestClient testClient;


  @Test
  public void criarUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriado_Status201(){
    UsuarioResponseDto responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("sansa@stark.com", "123456"))
        .exchange()
        .expectStatus().isCreated()
        .expectBody(UsuarioResponseDto.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getId()).isNotNull();
    Assertions.assertThat(responseBody.getUsername()).isEqualTo("sansa@stark.com");
    Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
  }


  @Test
  public void criarUsuario_ComUsernameInvalido_RetornarErrorMessage_Status422(){
    ErrorMessage responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

    responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("ana@email", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

    responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("ana@email.", "123456"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);
  }

@Test
  public void criarUsuario_ComPasswordInvalido_RetornarErrorMessage_Status422(){
    ErrorMessage responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("robert@baratheon.com", ""))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

  responseBody = testClient
      .post()
      .uri("/api/v1/usuarios")
      .contentType(MediaType.APPLICATION_JSON)
      .bodyValue(new UsuarioCreateDto("robert@baratheon.com", "12345"))
      .exchange()
      .expectStatus().isEqualTo(422)
      .expectBody(ErrorMessage.class)
      .returnResult().getResponseBody();

  Assertions.assertThat(responseBody).isNotNull();
  Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

    responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("robert@baratheon.com", "12345678"))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

  }


  @Test
  public void criarUsuario_ComUsernameJaCadastrado_RetornarErrorMessage_Status409(){
    ErrorMessage responseBody = testClient
        .post()
        .uri("/api/v1/usuarios")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioCreateDto("ana@email.com", "123456"))
        .exchange()
        .expectStatus().isEqualTo(409)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(409);
  }

  @Test
  public void buscarUsuario_ComIdExistente_RetornarUsuario_Status200(){
    UsuarioResponseDto responseBody = testClient
        .get()
        .uri("/api/v1/usuarios/1")
        .exchange()
        .expectStatus().isOk()
        .expectBody(UsuarioResponseDto.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getId()).isEqualTo(1);
    Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
    Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
  }

 @Test
  public void buscarUsuario_ComIdInexistente_RetornarErrorMessage_Status404(){
   ErrorMessage responseBody = testClient
        .get()
        .uri("/api/v1/usuarios/100")
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);

  }

  @Test
  public void editarSenha_ComDadosValidos_RetornarStatus204(){
    String responseBody = String.valueOf(testClient
        .patch()
        .uri("/api/v1/usuarios/2")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioSenhaDto("123456", "654789", "654789"))
        .exchange()
        .expectStatus().isOk()
        .expectBody(String.class)
            .returnResult().getResponseBody());

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody).isEqualTo("Senha alterada com sucesso!");

  }

  @Test
  public void editarSenha_ComIdInexistennte_RetornarStatus404(){
    ErrorMessage responseBody = testClient
        .patch()
        .uri("/api/v1/usuarios/6")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioSenhaDto("123456", "654789", "654789"))
        .exchange()
        .expectStatus().isNotFound()
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(404);
  }

 @Test
  public void editarSenha_ComCamposInvalidos_RetornarErrorMessage_Status422(){
    ErrorMessage responseBody = testClient
        .patch()
        .uri("/api/v1/usuarios/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioSenhaDto("", "", ""))
        .exchange()
        .expectStatus().isEqualTo(422)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

   responseBody = testClient
       .patch()
       .uri("/api/v1/usuarios/1")
       .contentType(MediaType.APPLICATION_JSON)
       .bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
       .exchange()
       .expectStatus().isEqualTo(422)
       .expectBody(ErrorMessage.class)
       .returnResult().getResponseBody();

   Assertions.assertThat(responseBody).isNotNull();
   Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);

   responseBody = testClient
       .patch()
       .uri("/api/v1/usuarios/1")
       .contentType(MediaType.APPLICATION_JSON)
       .bodyValue(new UsuarioSenhaDto("1234567", "1234567", "1234567"))
       .exchange()
       .expectStatus().isEqualTo(422)
       .expectBody(ErrorMessage.class)
       .returnResult().getResponseBody();

   Assertions.assertThat(responseBody).isNotNull();
   Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(422);


 }


@Test
  public void editarSenha_ComSenhasInvalidos_RetornarErrorMessage_Status400(){
    ErrorMessage responseBody = testClient
        .patch()
        .uri("/api/v1/usuarios/1")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(new UsuarioSenhaDto("123456", "987654", "987651"))
        .exchange()
        .expectStatus().isEqualTo(400)
        .expectBody(ErrorMessage.class)
        .returnResult().getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(400);

   responseBody = testClient
       .patch()
       .uri("/api/v1/usuarios/1")
       .contentType(MediaType.APPLICATION_JSON)
       .bodyValue(new UsuarioSenhaDto("000000", "987654", "987654"))
       .exchange()
       .expectStatus().isEqualTo(400)
       .expectBody(ErrorMessage.class)
       .returnResult().getResponseBody();

   Assertions.assertThat(responseBody).isNotNull();
   Assertions.assertThat(responseBody.getStatusCode()).isEqualTo(400);
 }

  @Test
  public void buscarUsuarios_RetornarUsuarios_Status200(){
    List<UsuarioResponseDto> responseBody = testClient
        .get()
        .uri("/api/v1/usuarios")
        .exchange()
        .expectStatus().isOk()
        .expectBodyList(UsuarioResponseDto.class)
        .returnResult()
        .getResponseBody();

    Assertions.assertThat(responseBody).isNotNull();
    Assertions.assertThat(responseBody.size()).isEqualTo(3);
//    Assertions.assertThat(responseBody.getId()).isEqualTo(1);
//    Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
//    Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
  }


}
