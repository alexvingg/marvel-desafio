package br.com.desafio.marvel.services;

import br.com.desafio.marvel.model.Authorization;
import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.model.Comic;
import br.com.desafio.marvel.model.PageCharacter;

import java.io.IOException;
import java.util.List;

/**
 * Created by Usuário on 29/11/2016.
 */
public interface MarvelService {

    boolean validServiceByToken(Authorization auth);

    Character getCharacterId(Long id);

    List<Character> getCharacterList(Integer offset);

    List<Comic> getComics(Long id) throws Exception;

    Long getTotal();

    PageCharacter getCharacter(Integer offset);

}
