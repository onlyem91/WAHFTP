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
    private HashMap<Integer, ContainerTag> elements = new HashMap<Integer, ContainerTag>();

    private enum htmlObjects {
        header, footer;
    }


    /**
     * This method is exposed as a REST service. The @RequestMapping parameter tells Spring that when a request comes in
     * to the server at the sub-url of '/welcome' (e.g. http://localhost:8080/codeApi-server/welcome)
     * it should be directed to this method.
     * <p/>
     * In normal SpringMVC you would typically handle the request, attach some data to the 'Model' and redirect to a
     * JSP for rendering. In our REST example however we want the result to be an XML response. Thanks to some Spring
     * magic we can just return our bean, annotate it with @ResponseBody and Spring will magically turn this into XML
     * for us.
     * <p/>
     * We really didn't need the whole ResponseMessage object here and could just have easily returned a String. That
     * wouldn't have made a very good example though, so the ResponseMessage is here to show how Spring turns objects
     * into XML and back again for easy REST calls. The 'date' parameter was added just to give it some spice.
     *
     * @param name the name of the person to say hello to. This is pulled from the input URL. In this case we use a
     *             request parameter (i.e. ?name=someone), but you could also map it directly into the URL if you
     *             prefer. See the very good SpringMVC documentation on this for more information.
     * @return
     */
    @RequestMapping("/welcome")
    public @ResponseBody
    ResponseMessage sayHello(@RequestParam(required = false) String name) {

        log.info("Saying hello to '{}'", name);

        String message;
        if (name != null && name.trim().length() > 0) {
            message = "Hello " + name;
        } else {
            message = "Hello mysterious person";
        }

        return new ResponseMessage("Successfully created HTML file.", 201);
    }

    @RequestMapping("/json")
    public @ResponseBody ResponseMessage test(@RequestParam(required = true) String objectName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        Classifier obj = mapper.readValue(objectName, Classifier.class);

        sayHello("header", obj.getValue());

        return new ResponseMessage("Message successfully redirected to method", 201);
    }

    @RequestMapping("/entry")
    public @ResponseBody ResponseMessage sayHello(@RequestParam(required = true) String objectName, @RequestParam(required = true) String objectColor) {

        switch (htmlObjects.valueOf(objectName)) {
            case header:
                changeColorOfHeader(objectColor);
                break;
            case footer:
                changeColorOfFooter(objectColor);
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

        head.with(
            title("Pretty App"),
            script().withSrc("http://livejs.com/live.js").withType("text/javascript"),
            link().withRel("stylesheet").withHref("https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css")
        );

        body.with(
            div().with(
                h1("Welcome to our pretty app!")
            ).withClass("page-header")
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

        elements.get(1).with(
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

        elements.get(0).withClass("navbar navbar-default navbar-fixed-top");

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

        elements.put(0, nav);
        elements.put(1, ul);

        if (!updateFile())
            return new ResponseMessage("Error creating HTML page", 500);

        return new ResponseMessage("Successfully updated HTML file.", 201);
    }

    private String renderHtml(){
        return document.render();
    }

    private void changeColorOfHeader(String objectColor){
        body.with(
            h1(objectColor).attr("style", "color:"+objectColor+";").withClass("example")
        );

        updateFile();
    }

    private void changeColorOfFooter(String objectColor){
        // TODO
    }
}
