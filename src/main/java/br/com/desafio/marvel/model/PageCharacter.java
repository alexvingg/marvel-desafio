package br.com.desafio.marvel.model;

import java.util.List;

/**
 * Created by Usuário on 30/11/2016.
 */
public class PageCharacter {

    private List<Character> characterList;

    private Integer total;

    private Integer offset;

    private Integer count;

    public List<Character> getCharacterList() {
        return characterList;
    }

    public void setCharacterList(List<Character> characterList) {
        this.characterList = characterList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
