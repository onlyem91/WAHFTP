package nl.hackergames.pizza.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import j2html.tags.ContainerTag;
import nl.hackergames.pizza.common.ResponseMessage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import static j2html.TagCreator.*;

/**
 * SpringMVC Controller that lives on the server side and handles incoming HTTP requests. It is basically a servlet but
 * using the power of SpringMVC we can avoid a lot of the raw servlet and request/response mapping uglies that
 * servlets require and instead just deal with simple, clean Java Objects. For more information on SpringMVC see:
 * http://static.springsource.org/spring/docs/current/spring-framework-reference/html/mvc.html
 */
@Controller
public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    private ContainerTag head = head();
    private ContainerTag body = body();

    private ContainerTag document = html().with(head, body);

    private HashMap<Integer, ContainerTag> elements = new HashMap<>();

    @RequestMapping("/json")
    public @ResponseBody ResponseMessage determineMessage(@RequestParam(required = true) String objectName) throws IOException {

        ObjectMapper mapper = new ObjectMapper();

        Classifier classifier = mapper.readValue(objectName, Classifier.class);

        ObjectModifier objectModifier = new ObjectModifier(head, body, elements);

        switch (classifier.getAttribute()) {
            case "color":
                objectModifier.changeColor(document, classifier.getObject(), classifier.getValue());
                break;
            case "width":
                objectModifier.changeWidth(document, classifier.getObject(), classifier.getValue());
                break;
            case "height":
                objectModifier.changeHeight(document, classifier.getObject(), classifier.getValue());
                break;
            default:
                break;
        }

        return new ResponseMessage("Message successfully redirected to method", 201);
    }

    /*
     * METHODS OF NADIA & ROY
     */

    @RequestMapping("/init")
    public @ResponseBody ResponseMessage init(){

        ContainerTag header = h1("header");
        ContainerTag content = h2("content");

        elements.put(0, header);
        elements.put(1, content);

        body.with(
                header,
                content
        );

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
            return new ResponseMessage("Error creating HTML page", 500);
        }

        return new ResponseMessage("Successfully created HTML file.", 201);
    }

}
