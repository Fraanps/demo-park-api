package br.com.fps.portfolio.demoparkapi.entity;

import br.com.fps.portfolio.demoparkapi.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_usuario")
  private Long id;

  @Column(name = "username", nullable = false, unique = true, length = 100)
  private String username;

  @Column(name = "password", nullable = false, length = 200)
  private String password;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false, length = 25)
  private Role role = Role.ROLE_CLIENTE;

  // campos de auditoria
  @Column(name="data_criacao")
  private LocalDateTime dataCriacao;
  @Column(name="data_modificacao")
  private LocalDateTime dataModificacao;
  @Column(name="criado_por")
  private String criadoPor;
  @Column(name="modificado_por")
  private String modificadoPor;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Usuario usuario = (Usuario) o;
    return Objects.equals(id, usuario.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

}
