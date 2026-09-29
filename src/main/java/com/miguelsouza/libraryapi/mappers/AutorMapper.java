package com.miguelsouza.libraryapi.mappers;

import com.miguelsouza.libraryapi.dto.AutorDTO;
import com.miguelsouza.libraryapi.model.Autor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AutorMapper {

    @Mapping(source = "nome", target = "nome")
    @Mapping(source = "dataNascimento", target = "dataNascimento")
    @Mapping(source = "nacionalidade", target = "nacionalidade")
    Autor toEntity(AutorDTO dto);
    AutorDTO toDTO(Autor autor);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    @Mapping(target = "usuario", ignore = true)
    @Mapping(target = "livros", ignore = true)
    void updateEntityFromDTO(AutorDTO dto, @MappingTarget Autor autor);
}
