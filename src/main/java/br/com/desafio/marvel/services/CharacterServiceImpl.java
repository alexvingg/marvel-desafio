package br.com.desafio.marvel.services;

import br.com.desafio.marvel.configuration.AppConfig;
import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.model.PageCharacter;
import br.com.desafio.marvel.repository.CharactersRepository;
import br.com.desafio.marvel.threads.MarvelTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by alexcosta on 27/11/16.
 */
@Service
public class CharacterServiceImpl implements CharacterService {

    @Autowired
    private CharactersRepository charactersRepository;

    @Autowired
    private MarvelService marvelService;

    @Override
    public Page<Character> getAllCharacter(Pageable pageable){
        return charactersRepository.findAll(pageable);
    }

    @Override
    public List<Character> getAllCharacter() {
        return charactersRepository.findAll();
    }

    @Override
    public void save(List<Character> character) {
        charactersRepository.save(character);
    }

    @Override
    public Character findByIdCharacter(Long id) {
        return charactersRepository.findOne(id);
    }

    @Override
    public void verifyCharacters(){
        List<Character> characterList = marvelService.getCharacterList(0);
        charactersRepository.save(characterList);

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext();
        ctx.register(AppConfig.class);
        ctx.refresh();
        MarvelTask task = ctx.getBean(MarvelTask.class);
        task.setMarvelService(marvelService);
        task.setCharactersRepository(charactersRepository);
        task.setAuthentication(SecurityContextHolder.getContext().getAuthentication());
        task.doMarvelTask();
    }

    @Override
    public PageCharacter getCharacters(Integer offset) {

        PageCharacter pageCharacter = marvelService.getCharacter(offset);
        return pageCharacter;
    }
}
