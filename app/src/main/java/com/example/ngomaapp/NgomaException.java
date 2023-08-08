package com.example.ngomaapp;

public class NgomaException extends Exception {
    private final String title;
    public NgomaException(String title, String message) {
        super(message);
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

}
