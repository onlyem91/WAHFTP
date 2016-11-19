package nl.hackergames.pizza.server.controller;

/**
 * Created by ROYKOK on 18-11-2016.
 */
public class Classifier {

    private String confidence;
    private String value;

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
