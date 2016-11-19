package nl.hackergames.pizza.server.controller;

import j2html.tags.ContainerTag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;

/**
 * Created by ROYKOK on 19-11-2016.
         */
public class ObjectModifier {

    private ContainerTag body;
    private ContainerTag head;
    private HashMap<String, ContainerTag> elements;

    public ObjectModifier(ContainerTag head, ContainerTag body, HashMap<String, ContainerTag> elements){
        this.head = head;
        this.body = body;
        this.elements = elements;
    }

    public void changeColor(ContainerTag document, String object, String value){
            // get object from html (object)
            ContainerTag objectTag = elements.get(object);

            //set header with new value
            objectTag.attr("style", "background-color:"+value+";");

            renderDocument(document);
    }

    public void changeWidth(ContainerTag document, String object, String value){
        String style = "";
        String width = "";

        switch (value){
            case "smaller":
                style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");
                width = between(style, "width:", "%; height");

                style = style.replace("width:" + width + "%;", "width:" + (Integer.parseInt(width) - 10) + "%;");
                elements.get(object).attr("style", style);

                break;
            case "bigger":
                style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");
                width = between(style, "width:", "%; height");

                style = style.replace("width:" + width + "%;", "width:" + (Integer.parseInt(width) + 10) + "%;");
                elements.get(object).attr("style", style);

                break;
        }

        renderDocument(document);
    }

    public void changeHeight(ContainerTag document, String object, String value){
        String style = "";
        String height = "";

        switch (value){
            case "smaller":
                style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");
                height = between(style, "height:", "px;");

                style = style.replace("height:" + height + "px;", "height:" + (Integer.parseInt(height) - 100) + "px;");
                elements.get(object).attr("style", style);

                break;
            case "bigger":
                style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");
                height = between(style, "height:", "px;");

                style = style.replace("height:" + height + "px;", "height:" + (Integer.parseInt(height) + 100) + "px;");
                elements.get(object).attr("style", style);

                break;
        }

        renderDocument(document);
    }

    public static String between(String value, String a, String b) {
        // Return a substring between the two strings.
        int posA = value.indexOf(a);
        if (posA == -1) {
            return "";
        }
        int posB = value.lastIndexOf(b);
        if (posB == -1) {
            return "";
        }
        int adjustedPosA = posA + a.length();
        if (adjustedPosA >= posB) {
            return "";
        }
        return value.substring(adjustedPosA, posB);
    }

    public void changeAllignment(ContainerTag document, String object, String value) {
        if("nav".equals(object)){
            elements.get(object).withClass("navbar navbar-default navbar-fixed-" + value);
        } else {
            String style = "";
            switch (value) {
                case "right":
                    style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");
                    style = style.replace("margin-right:auto;", "");

                    if(!style.contains("margin-left:auto"))
                        style += "margin-left:auto;";

                    elements.get(object).attr("style", style);
                    break;
                case "left":
                    style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");
                    style = style.replace("margin-left:auto;", "");

                    if(!style.contains("margin-right:auto"))
                        style += "margin-right:auto;";

                    elements.get(object).attr("style", style);
                    break;
                case "center":
                    style = between(elements.get(object).renderOpenTag(), "style=\"", "\"");

                    if(!style.contains("margin-right:auto"))
                        style += "margin-right:auto;";

                    if(!style.contains("margin-left:auto"))
                        style += "margin-left:auto;";

                    elements.get(object).attr("style", style);
                    break;
            }
        }
        renderDocument(document);
    }


    private void renderDocument(ContainerTag document){
        Document doc = Jsoup.parse(document.render());

        try{
            File file = new File("index.html");
            FileOutputStream fop = new FileOutputStream(file);

            // get the content in bytes
            byte[] contentInBytes = doc.html().getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();
        }catch(Exception e){

        }
    }
}
