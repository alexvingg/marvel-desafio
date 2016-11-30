package br.com.desafio.marvel.model;

/**
 * Created by Usuário on 29/11/2016.
 */
public class Authorization {

    private String publicToken;

    private String privateToken;

    public String getPublicToken() {
        return publicToken;
    }

    public void setPublicToken(String publicToken) {
        this.publicToken = publicToken;
    }

    public String getPrivateToken() {
        return privateToken;
    }

    public void setPrivateToken(String privateToken) {
        this.privateToken = privateToken;
    }
}
