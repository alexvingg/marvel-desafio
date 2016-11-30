package br.com.desafio.marvel.threads;

import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.repository.CharactersRepository;
import br.com.desafio.marvel.services.MarvelService;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.List;

/**
 * Created by alexcosta on 29/11/16.
 */
@Configurable
public class MarvelTask {

    private MarvelService marvelService;

    private CharactersRepository charactersRepository;

    private Authentication authentication;

    @Async
    public void doMarvelTask(){

        try {

            SecurityContext context = new SecurityContextImpl();
            context.setAuthentication(this.authentication);

            SecurityContextHolder.setContext(context);
            Long total = marvelService.getTotal();
            Integer offset = 1;

            while(offset == 1 || ((offset + 1) * 20) < total){
                List<Character> characterList = marvelService.getCharacterList(offset);
                charactersRepository.save(characterList);
                offset++;
            }

        }finally {
            SecurityContextHolder.clearContext();
        }

    }

    public MarvelService getMarvelService() {
        return marvelService;
    }

    public void setMarvelService(MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    public CharactersRepository getCharactersRepository() {
        return charactersRepository;
    }

    public void setCharactersRepository(CharactersRepository charactersRepository) {
        this.charactersRepository = charactersRepository;
    }

    public Authentication getAuthentication() {
        return authentication;
    }

    public void setAuthentication(Authentication authentication) {
        this.authentication = authentication;
    }
}
