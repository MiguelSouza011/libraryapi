package com.miguelsouza.libraryapi.repository.specs;

import com.miguelsouza.libraryapi.model.Livro;
import com.miguelsouza.libraryapi.model.enums.GeneroLivro;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn) {
        return (root, query, cb) ->
                cb.equal(cb.trim(root.get("isbn")), isbn.trim());
    }

    public static Specification<Livro> tituloLike(String titulo) {
        return (root, query, cb) ->
                cb.like( cb.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%");
    }

    public static Specification<Livro> generoEqual(GeneroLivro generoLivro) {
        return (root, query, cb) ->
                cb.equal( root.get("genero"), generoLivro);
    }

    public static Specification<Livro> anoPublicacao(Integer anoPublicacao) {
        return (root, query, cb) ->
                cb.equal( cb.function("to_char", String.class,
                        root.get("dataPublicacao"), cb.literal("YYYY")),
                        anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nome) {
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.LEFT);
            return cb.like(cb.upper(joinAutor.get("nome")), "%" + nome.toUpperCase() + "%");
        };
        //cb.like( cb.upper(root.get("autor").get("nome")), "%" + nome.toUpperCase() + "%");
    }
}
