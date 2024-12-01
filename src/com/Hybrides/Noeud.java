package com.Hybrides;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Noeud {
	@JsonProperty("character")
	public char character;
	@JsonProperty("isEndOfWord")
    public boolean isEndOfWord;
	@JsonProperty("left")
    public Noeud left; 
	@JsonProperty("middle")
    public Noeud middle;
	@JsonProperty("right")
    public Noeud right;

    public Noeud(char character,Noeud left,Noeud middle,Noeud right,boolean end) {
        this.character = character;
        this.isEndOfWord = end;
        this.left = left;
        this.middle = middle;
        this.right = right;
    }
    public Noeud() {
    	
    }    
    }

