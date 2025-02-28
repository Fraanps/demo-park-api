package br.com.fps.portfolio.demoparkapi.web.dto.mapper;

import br.com.fps.portfolio.demoparkapi.entity.Usuario;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioCreateDto;
import br.com.fps.portfolio.demoparkapi.web.dto.UsuarioResponseDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.TypeMap;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioMapper {

  public static Usuario toUsuario(UsuarioCreateDto usuarioCreateDto){
    return new ModelMapper().map(usuarioCreateDto, Usuario.class);
  }

  public static UsuarioResponseDto toDto( Usuario usuario) {
    // editando as roles
    String role = usuario.getRole().name().substring("ROLE_".length());

    ModelMapper modelMapper = new ModelMapper();
    TypeMap<Usuario, UsuarioResponseDto> propertyMap = modelMapper.createTypeMap(usuario, UsuarioResponseDto.class);

    propertyMap.addMappings(mapper -> mapper.map(src -> role, UsuarioResponseDto::setRole));
    return modelMapper.map(usuario, UsuarioResponseDto.class);
  }

  public static  List<UsuarioResponseDto> toListDto(List<Usuario> usuarios) {
    // transformando cada objeto usuario e transformando em um objeto response dto e adicionar numa lista nova
    return usuarios.stream().map(user -> toDto(user)).collect(Collectors.toList());

  }
}
