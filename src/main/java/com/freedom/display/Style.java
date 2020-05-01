package com.freedom.display;

public class Style {
    private char character;
    private String frontColour;

    public Style(char character, String frontColour) {
        this.character = character;
        this.frontColour = frontColour;
    }

    public char getCharacter() {
        return character;
    }

    public String getFrontColour() {
        return frontColour;
    }
}
