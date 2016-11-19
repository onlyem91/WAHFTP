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
    private HashMap<String, ContainerTag> elements = new HashMap<>();

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
            case "allignment":
                objectModifier.changeAllignment(document, classifier.getObject(), classifier.getValue());
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

        body = body();
        head = head();
        document = html().with(head, body);

        ContainerTag header = div().with(
                h1("header")
        ).withClass("jumbotron");

        ContainerTag content = p("content");
        ContainerTag container = div().with().withClass("container");

        elements.put("header", header);
        elements.put("content", content);
        elements.put("container", container);

        body.with(
                container.with(
                    header,
                    content
                ).attr("style", "margin-top:75px;")
        );

        head.with(
            title("Pretty App"),
            script().withSrc("http://livejs.com/live.js").withType("text/javascript"),
            link().withRel("stylesheet").withHref("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css")
        );

        if (!updateFile())
            return new ResponseMessage("Error creating HTML page", 500);

        return new ResponseMessage("Successfully created HTML file.", 201);
    }

    private boolean updateFile(){
        Document doc = Jsoup.parse(renderHtml());

        try{
            File file = new File("index.html");
            FileOutputStream fop = new FileOutputStream(file);

            // get the content in bytes
            byte[] contentInBytes = doc.html().getBytes();

            fop.write(contentInBytes);
            fop.flush();
            fop.close();

            return true;

        }catch(Exception e){
            return false;
        }

    }

    @RequestMapping("/addNavItem")
    private @ResponseBody ResponseMessage addNavItem (String name, String url, String active) {

        String liClass = "";

        if(active.equals("true")){
            liClass = "active";
        }

        elements.get("ul").with(
            li().with(
                a().withHref(url).withText(name)
            ).withClass(liClass)
        );

        if (!updateFile())
            return new ResponseMessage("Error creating HTML page", 500);

        return new ResponseMessage("Successfully updated HTML file.", 201);

    }


    @RequestMapping("/moveNavbar")
    private @ResponseBody ResponseMessage moveNavbar (String position) {

        elements.get("nav").withClass("navbar navbar-default navbar-fixed-" + position);


        if (!updateFile())
            return new ResponseMessage("Error creating HTML page", 500);

        return new ResponseMessage("Successfully updated HTML file.", 201);

    }

    @RequestMapping("/addNavbar")
    private @ResponseBody ResponseMessage addNavbar (){
        ContainerTag ul = ul().withClass("nav navbar-nav");

        ContainerTag nav = nav().with(
            div().withClass("container").with(
                div().with(
                    ul
                ).withClass("collapse navbar-collapse")
            )
        ).withClass("navbar navbar-default");

       body.with(
            nav
        );

        elements.put("nav", nav);
        elements.put("ul", ul);

        if (!updateFile())
            return new ResponseMessage("Error creating HTML page", 500);

        return new ResponseMessage("Successfully updated HTML file.", 201);
    }

    private String renderHtml(){
        return document.render();
    }

}
