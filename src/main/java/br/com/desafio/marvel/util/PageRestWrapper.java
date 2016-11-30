package br.com.desafio.marvel.util;

import br.com.desafio.marvel.model.Character;
import br.com.desafio.marvel.model.PageCharacter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexcosta on 27/11/16.
 */
public class PageRestWrapper<T> {
    public static final int MAX_PAGE_ITEM_DISPLAY = 20;

    private PageCharacter pageCharacter;

    @JsonIgnore
    private List<PageItem> items;
    private int currentNumber;

    @JsonIgnore
    private String url;

    @JsonIgnore
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PageRestWrapper(PageCharacter pageCharacter, String url){

        this.pageCharacter = pageCharacter;
        this.url = url;
        items = new ArrayList<PageItem>();

        currentNumber = pageCharacter.getOffset()+ 1; //start from 1 to match page.page

        int start, size;
        if (this.getTotalPages() <= MAX_PAGE_ITEM_DISPLAY){
            start = 1;
            size = this.getTotalPages();
        } else {
            if (currentNumber <= MAX_PAGE_ITEM_DISPLAY - MAX_PAGE_ITEM_DISPLAY/2){
                start = 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else if (currentNumber >= this.getTotalPages() - MAX_PAGE_ITEM_DISPLAY/2){
                start = this.getTotalPages() - MAX_PAGE_ITEM_DISPLAY + 1;
                size = MAX_PAGE_ITEM_DISPLAY;
            } else {
                start = currentNumber - MAX_PAGE_ITEM_DISPLAY/2;
                size = MAX_PAGE_ITEM_DISPLAY;
            }
        }

        for (int i = 0; i<size; i++){
            items.add(new PageItem(start+i, (start+i)==currentNumber));
        }
    }

    @JsonIgnore
    public List<PageItem> getItems(){
        return items;
    }

    public int getNumber(){
        return currentNumber;
    }

    public List<Character> getContent(){
        return pageCharacter.getCharacterList();
    }

    public int getSize(){
        return pageCharacter.getCharacterList().size();
    }

    public int getTotalPages(){

        if(pageCharacter.getTotal() %2 == 0){
            return pageCharacter.getTotal() / MAX_PAGE_ITEM_DISPLAY;
        }
        return pageCharacter.getTotal() / MAX_PAGE_ITEM_DISPLAY;
    }

    @JsonIgnore
    public boolean isFirstPage(){
        return pageCharacter.getOffset() == 0;
    }

    @JsonIgnore
    public boolean isLastPage(){

        Integer myOffset = 0;

        if(pageCharacter.getOffset() == 0){
            myOffset = 1;
        }

        return (myOffset * MAX_PAGE_ITEM_DISPLAY) + pageCharacter.getCount() == pageCharacter.getTotal();
    }

    @JsonIgnore
    public boolean isHasPreviousPage(){
        return pageCharacter.getOffset() > 1;
    }

    @JsonIgnore
    public boolean isHasNextPage(){

        Integer myOffset = 0;

        if(pageCharacter.getOffset() == 0){
            myOffset = 1;
        }

        return( myOffset * MAX_PAGE_ITEM_DISPLAY) +  pageCharacter.getCount() < pageCharacter.getTotal();
    }

    public class PageItem {
        private int number;
        private boolean current;
        public PageItem(int number, boolean current){
            this.number = number;
            this.current = current;
        }

        public int getNumber(){
            return this.number;
        }

        public boolean isCurrent(){
            return this.current;
        }
    }

}