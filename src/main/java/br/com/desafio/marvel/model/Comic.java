package br.com.desafio.marvel.model;

/**
 * Created by alexcosta on 27/11/16.
 */
public class Comic implements Comparable<Comic>{

    private String title;

    private String issueNumber;

    private String description;

    private String thumbnail;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    @Override
    public int compareTo(Comic comic) {
        if (Double.parseDouble(this.issueNumber) < Double.parseDouble(comic.issueNumber)) {
            return -1;
        }
        if (Double.parseDouble(this.issueNumber) > Double.parseDouble(comic.issueNumber)) {
            return 1;
        }
        return 0;
    }
}
