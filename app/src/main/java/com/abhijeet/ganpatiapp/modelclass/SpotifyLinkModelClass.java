package com.abhijeet.ganpatiapp.modelclass;

public class SpotifyLinkModelClass {

    private String name;
    private String spotifyLink;


    public SpotifyLinkModelClass(String name, String spotifyLink) {
        this.name = name;
        this.spotifyLink = spotifyLink;
    }

    public String getName() {
        return name;
    }

    public String getSpotifyLink() {
        return spotifyLink;
    }
}
