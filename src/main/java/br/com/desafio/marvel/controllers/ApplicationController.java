package br.com.desafio.marvel.controllers;

import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.model.Comic;
import br.com.desafio.marvel.services.CharacterService;
import br.com.desafio.marvel.services.MarvelService;
import br.com.desafio.marvel.util.PageRestWrapper;
import br.com.desafio.marvel.util.PageWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;

/**
 * Created by alexcosta on 25/11/16.
 */
@Controller
public class ApplicationController {

    @Autowired
    private CharacterService characterService;

    @Autowired
    private MarvelService marvelService;

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    @RequestMapping("/character")
    public String character(Model model, Pageable pageable) {
        List<Character> characterList = characterService.getAllCharacter();
        if(characterList.isEmpty()){
            characterService.verifyCharacters();
        }
        PageRestWrapper<Character> page = new PageRestWrapper<Character>
                (characterService.getCharacters(pageable.getPageNumber()), "/character");

        model.addAttribute("page", page);

        return "character";
    }

    @RequestMapping("/character/{id}")
    public String detail(@PathVariable("id") Long id, Model model) throws Exception {
        Character character = marvelService.getCharacterId(id);
        model.addAttribute("character", character);
        int offset = 0;
        RestTemplate restTemplate = new RestTemplate();
        Integer total = 0;
        List<Comic> comics = marvelService.getComics(character.getIdCharacter());
        Collections.sort(comics);
        model.addAttribute("comics", comics);
        return "detail";
    }
}