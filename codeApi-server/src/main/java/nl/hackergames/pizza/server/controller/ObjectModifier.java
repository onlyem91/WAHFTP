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
    private HashMap<Integer, ContainerTag> elements;

    public ObjectModifier(ContainerTag head, ContainerTag body, HashMap<Integer, ContainerTag> elements){
        this.head = head;
        this.body = body;
        this.elements = elements;
    }

    public void changeColor(ContainerTag document, String object, String value){
            // get object from html (object)
            ContainerTag objectTag = elements.get(0);
            //body.with()

            //set header with new value
            objectTag.attr("style", "color:"+value+";");

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

    public void changeWidth(ContainerTag document, String object, String value){

    }

    public void changeHeight(ContainerTag document, String object, String value){

    }


}