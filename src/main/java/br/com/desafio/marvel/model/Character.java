package br.com.desafio.marvel.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by alexcosta on 27/11/16.
 */
@Entity(name = "character")
public class Character {

    @Id
    @Column(name = "id")
    @GeneratedValue
    private Long id;

    @Column(name = "idCharacter")
    private Long idCharacter;

    @Column(name = "name")
    private String name;

    @Column(name = "description", length = 3000)
    private String description;

    @Column(name = "modified")
    @DateTimeFormat(pattern="dd/MMM/YYYY")
    private Date modified;

    @Column(name = "thumbnail")
    private String thumbnail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getIdCharacter() {
        return idCharacter;
    }

    public void setIdCharacter(Long idCharacter) {
        this.idCharacter = idCharacter;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getDateFormated(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(this.modified);
    }
}
