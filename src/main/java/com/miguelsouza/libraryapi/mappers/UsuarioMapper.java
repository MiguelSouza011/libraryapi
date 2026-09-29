package com.miguelsouza.libraryapi.mappers;

import com.miguelsouza.libraryapi.dto.UsuarioDTO;
import com.miguelsouza.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
