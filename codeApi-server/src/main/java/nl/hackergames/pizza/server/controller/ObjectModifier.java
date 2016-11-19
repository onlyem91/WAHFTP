package nl.hackergames.pizza.server.controller;

import j2html.tags.ContainerTag;
import j2html.tags.Tag;
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

    }

    public void changeHeight(ContainerTag document, String object, String value){

    }

    public void changeAllignment(ContainerTag document, String object, String value) {
        if("nav".equals(object)){
            elements.get(object).withClass("navbar navbar-default navbar-fixed-" + value);
        } else {
            switch (value) {
                case "right":
                    elements.get(object).attr("style", "margin-left:auto;");
                    break;
                case "left":
                    elements.get(object).attr("style", "margin-right:auto;");
                    break;
                case "center":
                    elements.get(object).attr("style", "margin-right:auto; margin-left:auto;");

                    System.out.printf(elements.get(object).renderOpenTag());

                    for (Tag t:elements.get(object).children) {
                        System.out.println(t.toString());
                    }

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
