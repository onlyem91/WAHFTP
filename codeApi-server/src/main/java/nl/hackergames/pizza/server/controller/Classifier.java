package nl.hackergames.pizza.server.controller;

/**
 * Created by ROYKOK on 18-11-2016.
 */
public class Classifier {

    private String value;
    private String object;
    private String attribute;

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
