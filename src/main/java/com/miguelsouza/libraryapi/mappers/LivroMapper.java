package com.miguelsouza.libraryapi.mappers;

import com.miguelsouza.libraryapi.dto.CadastroLivroDTO;
import com.miguelsouza.libraryapi.dto.PesquisaLivroDTO;
import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.repository.AutorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = AutorMapper.class)
public abstract class LivroMapper {

    @Autowired
    AutorRepository autorRepository;
    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null))")
    public abstract Livro toEntity(CadastroLivroDTO dto);
    public abstract PesquisaLivroDTO toDTO(Livro livro);

    @Mapping(target = "autor", expression = "java( autorRepository.findById(dto.idAutor()).orElse(null))")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    @Mapping(target = "dataAtualizacao", ignore = true)
    public abstract void updateEntityFromDTO(CadastroLivroDTO dto, @MappingTarget Livro livro);
}
