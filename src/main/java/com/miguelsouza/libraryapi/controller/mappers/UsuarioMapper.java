package com.miguelsouza.libraryapi.controller.mappers;

import com.miguelsouza.libraryapi.controller.dto.UsuarioDTO;
import com.miguelsouza.libraryapi.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
}
