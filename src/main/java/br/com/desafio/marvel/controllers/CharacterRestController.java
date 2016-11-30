package br.com.desafio.marvel.controllers;

import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.services.CharacterService;
import br.com.desafio.marvel.util.PageWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Usuário on 29/11/2016.
 */
@RestController
public class CharacterRestController {

    @Autowired
    private CharacterService characterService;

    @RequestMapping("/characters")
    public PageWrapper<Character> getCharacterList(Pageable pageable){
        PageWrapper<Character> page = new PageWrapper<Character>
                (characterService.getAllCharacter(pageable), "/character");
        return page;
    }
}
