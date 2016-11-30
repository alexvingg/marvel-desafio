package br.com.desafio.marvel.services;

import br.com.desafio.marvel.controllers.ApplicationController;
import br.com.desafio.marvel.mocks.CharacterPojo;
import br.com.desafio.marvel.mocks.Results;
import br.com.desafio.marvel.model.Authorization;
import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.model.Comic;
import br.com.desafio.marvel.model.PageCharacter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuário on 29/11/2016.
 */
@Service
public class MarvelServiceImpl implements MarvelService{

    private static final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    private final static String URL_CHARACTER_COMICS = "http://gateway.marvel.com/v1/public/characters/%s/comics?" +
            "apikey=%s&ts=%s&hash=%s&limit=%s&offset=%s";

    private final static String URL_CHARACTERS = "http://gateway.marvel.com/v1/public/characters?" +
            "apikey=%s&ts=%s&hash=%s&limit=%s&offset=%s";

    private final static String URL_CHARACTER = "http://gateway.marvel.com/v1/public/characters/%s?" +
            "apikey=%s&ts=%s&hash=%s&limit=%s";

    @Override
    public boolean validServiceByToken(Authorization auth) {
        Long timeStamp = System.currentTimeMillis();
        String url = String.format(URL_CHARACTERS, auth.getPublicToken(), timeStamp, this.getHashAutentication(auth, timeStamp), 1, 1);

        RestTemplate restTemplate = new RestTemplate();

        try{
            restTemplate.getForEntity(url, String.class);
        }catch (HttpClientErrorException ex){
            log.info("Erro ao acessar api", ex);
            return false;
        }

        return true;
    }

    @Override
    public Character getCharacterId(Long id) {
        Authorization auth = (Authorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long timeStamp = System.currentTimeMillis();
        List<Character> characterList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(URL_CHARACTER, id ,auth.getPublicToken(), timeStamp, this.getHashAutentication(auth, timeStamp), 20);
        CharacterPojo pojo = restTemplate.getForObject(url, CharacterPojo.class);
        Character c = null;
        for (Results res:pojo.getData().getResults()) {
            c = new Character();
            c.setIdCharacter(Long.parseLong(res.getId()));
            c.setName(res.getName());
            c.setDescription(res.getDescription() != null ? res.getDescription() : "");
            c.setThumbnail(res.getThumbnail().getPath() + "/portrait_fantastic." + res.getThumbnail().getExtension());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                c.setModified(sdf.parse(res.getModified()));
            } catch (ParseException e) {
                log.error("Erro na conversao", e);
            }
        }

        return c;
    }

    @Override
    public Long getTotal(){
        Authorization auth = (Authorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long timeStamp = System.currentTimeMillis();
        String url = String.format(URL_CHARACTERS, auth.getPublicToken(), timeStamp, this.getHashAutentication(auth, timeStamp), 1, 1);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response =
                restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(response.getBody());
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode data = root.path("data");
        Long total = data.get("total").asLong();

        return total;
    }

    @Override
    public List<Character> getCharacterList(Integer offset){
        Authorization auth = (Authorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long timeStamp = System.currentTimeMillis();
        List<Character> characterList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(URL_CHARACTERS, auth.getPublicToken(), timeStamp, this.getHashAutentication(auth, timeStamp), 20, offset);
        CharacterPojo pojo = restTemplate.getForObject(url, CharacterPojo.class);
        for (Results res:pojo.getData().getResults()) {
            Character c = new Character();
            c.setIdCharacter(Long.parseLong(res.getId()));
            c.setName(res.getName());
            c.setDescription(res.getDescription() != null ? res.getDescription() : "");
            c.setThumbnail(res.getThumbnail().getPath() + "/portrait_fantastic." + res.getThumbnail().getExtension());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                c.setModified(sdf.parse(res.getModified()));
            } catch (ParseException e) {
                log.error("Erro na conversao", e);
            }
            characterList.add(c);
        }
        return characterList;
    }

    @Override
    public PageCharacter getCharacter(Integer offset){
        Authorization auth = (Authorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long timeStamp = System.currentTimeMillis();
        List<Character> characterList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(URL_CHARACTERS, auth.getPublicToken(), timeStamp, this.getHashAutentication(auth, timeStamp), 20, offset);
        CharacterPojo pojo = restTemplate.getForObject(url, CharacterPojo.class);
        for (Results res:pojo.getData().getResults()) {
            Character c = new Character();
            c.setIdCharacter(Long.parseLong(res.getId()));
            c.setName(res.getName());
            c.setDescription(res.getDescription() != null ? res.getDescription() : "");
            c.setThumbnail(res.getThumbnail().getPath() + "/portrait_fantastic." + res.getThumbnail().getExtension());
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
                c.setModified(sdf.parse(res.getModified()));
            } catch (ParseException e) {
                log.error("Erro na conversao", e);
            }
            characterList.add(c);
        }

        PageCharacter pageCharacter = new PageCharacter();
        pageCharacter.setCharacterList(characterList);
        pageCharacter.setCount(Integer.parseInt(pojo.getData().getCount()));
        pageCharacter.setTotal(Integer.parseInt(pojo.getData().getTotal()));
        pageCharacter.setOffset(Integer.parseInt(pojo.getData().getOffset()));

        return pageCharacter;
    }

    public List<Comic> getComics(Long id) throws Exception {
        Authorization auth = (Authorization) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long timeStamp = System.currentTimeMillis();
        int offset = 0;
        RestTemplate restTemplate = new RestTemplate();
        Integer total = 0;
        List<Comic> comics = new ArrayList<>();
        while(offset == 0 || ((offset + 1) * 20) < total){
            String url = String.format(URL_CHARACTER_COMICS, id, auth.getPublicToken(),timeStamp, getHashAutentication(auth,timeStamp), 20, offset);
            ResponseEntity<String> response =
                    restTemplate.getForEntity(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode data = root.path("data");
            total = Integer.parseInt(data.get("total").asText());
            ArrayNode array = (ArrayNode) data.path("results");
            for (JsonNode json : array) {
                Comic comic = new Comic();
                comic.setTitle(json.get("title").toString());
                comic.setIssueNumber(json.get("issueNumber").asText());
                comic.setDescription(json.get("description").equals("null") ? "" : json.get("description").asText());
                String img =  json.path("thumbnail").get("path").asText() + "/portrait_medium." +
                        json.path("thumbnail").get("extension").asText();
                comic.setThumbnail(img);
                comics.add(comic);
            }
            offset++;
        }

        return comics;
    }

    private String getHashAutentication(Authorization authorization, Long timeStamp){
        return DigestUtils.md5Hex(timeStamp.toString()+authorization.getPrivateToken()+authorization.getPublicToken());
    }
}
