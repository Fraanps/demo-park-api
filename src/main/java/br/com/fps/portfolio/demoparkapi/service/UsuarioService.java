package br.com.fps.portfolio.demoparkapi.service;

import br.com.fps.portfolio.demoparkapi.entity.Usuario;
import br.com.fps.portfolio.demoparkapi.exception.EntityNotFoundException;
import br.com.fps.portfolio.demoparkapi.exception.PasswordInvalidException;
import br.com.fps.portfolio.demoparkapi.exception.UsernameUniqueViolationException;
import br.com.fps.portfolio.demoparkapi.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static br.com.fps.portfolio.demoparkapi.config.constants.Messages.*;

@RequiredArgsConstructor
@Service
public class UsuarioService {

  private final UsuarioRepository usuarioRepository;

//  public UsuarioService(UsuarioRepository usuarioRepository) {
//    this.usuarioRepository = usuarioRepository;
//  }

  @Transactional
  public Usuario salvar(Usuario usuario) {
    try {
      return usuarioRepository.save(usuario);
    } catch (DataIntegrityViolationException ex) {
      throw new UsernameUniqueViolationException(String.format(USERNAME_JA_EXISTE, usuario.getUsername()));
    }
  }

  @Transactional(readOnly = true)
  public List<Usuario> getAllUsers() {
    return usuarioRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Usuario getUserById(Long id) {
    return usuarioRepository.findById(id).orElseThrow(
        () -> new EntityNotFoundException(String.format(USUARIO_NAO_ENCONTRADO, id))
    );
  }

  @Transactional
  public String editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
    // validação de nova senha
    if (!novaSenha.equals(confirmaSenha)) {
      throw new PasswordInvalidException((ERRO_CONFIRMACAO_DE_SENHA));
    }

    // validação se senha atual
    Usuario usuario = getUserById(id);
    if (!usuario.getPassword().equals(senhaAtual)) {
      throw new PasswordInvalidException(SENHA_NAO_CONFERE);

    }
    // se passar pelos ifs, altera a senha
    usuario.setPassword(novaSenha);
    usuarioRepository.save(usuario);
    return SENHA_ALTERADA_SUCESSO;
  }
}
