package br.com.desafio.marvel.services;

import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.model.PageCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by alexcosta on 27/11/16.
 */
public interface CharacterService {

    Page<Character> getAllCharacter(Pageable pageable);

    List<Character> getAllCharacter();

    void save(List<Character> character);

    Character findByIdCharacter(Long id);

    void verifyCharacters();

    PageCharacter getCharacters(Integer offset);

}
