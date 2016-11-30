package br.com.desafio.marvel.repository;

import br.com.desafio.marvel.model.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by alexcosta on 27/11/16.
 */
public interface CharactersRepository extends JpaRepository<Character, Long>{

}
