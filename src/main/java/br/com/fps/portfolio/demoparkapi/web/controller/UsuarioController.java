package br.com.fps.portfolio.demoparkapi.web.controller;

import br.com.fps.portfolio.demoparkapi.entity.Usuario;
import br.com.fps.portfolio.demoparkapi.service.UsuarioService;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioCreateDto;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioResponseDto;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioSenhaDto;
import br.com.fps.portfolio.demoparkapi.web.dto.mapper.UsuarioMapper;
import br.com.fps.portfolio.demoparkapi.web.exceptions.ErrorMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Usuarios", description = "Contém todas as operações relativas aos recursos para cadastro, edição e leitura de um usuário.")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/usuarios")
public class UsuarioController {

  private final UsuarioService usuarioService;


  @Operation( summary = "Criar um novo usuário", description = "Recurso para criar um novo usuário",
      responses = {
        @ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UsuarioResponseDto.class))),
        @ApiResponse(responseCode = "409", description = "Usuário e-mail já cadastrado no sistema",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
        @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada inválidos",
          content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class)))
      })
  @PostMapping
  public ResponseEntity<UsuarioResponseDto> create(@Valid @RequestBody UsuarioCreateDto usuarioCreateDto){
    Usuario user = usuarioService.salvar(UsuarioMapper.toUsuario(usuarioCreateDto));
    return ResponseEntity.status(HttpStatus.CREATED).body(UsuarioMapper.toDto(user));
  }

  @GetMapping
  public ResponseEntity<List<UsuarioResponseDto>> getAllUsers(){
    List<Usuario> users = usuarioService.getAllUsers();
    return ResponseEntity.status(HttpStatus.OK).body(UsuarioMapper.toListDto(users));
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponseDto> getUser(@PathVariable Long id){
    Usuario user = usuarioService.getUserById(id);
    return ResponseEntity.ok(UsuarioMapper.toDto(user));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<String> updatePassword(@PathVariable Long id, @Valid @RequestBody UsuarioSenhaDto usuarioSenhaDto){
    // método com retorno void para não ter um corpo na resposta
    String mensagem = usuarioService.editarSenha(id, usuarioSenhaDto.getSenhaAtual(),
                                                  usuarioSenhaDto.getNovaSenha(),
                                                  usuarioSenhaDto.getConfirmaSenha());
    return ResponseEntity.ok(mensagem);
  }

}
