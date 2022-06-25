package main.java.collection;

import java.io.Serializable;

/**
 * Enum for human's mood
 */
public enum Mood implements Serializable {
    SADNESS("SADNESS"),
    APATHY("APATHY"),
    CALM("CALM"),
    RAGE("RAGE"),
    FRENZY("FRENZY");

    private final String mood;

    Mood(String mood) {
        this.mood = mood;
    }

    public String getMood() {
        return mood;
    }

    @Override
    public String toString() {
        return "Mood{" +
                "mood='" + mood + '\'' +
                '}';
    }
}
