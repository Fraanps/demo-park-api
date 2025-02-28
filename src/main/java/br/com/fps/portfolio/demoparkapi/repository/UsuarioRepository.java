package br.com.fps.portfolio.demoparkapi.repository;

import br.com.fps.portfolio.demoparkapi.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
