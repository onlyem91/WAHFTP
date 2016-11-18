package nl.hackergames.pizza.server.controller;

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

import static j2html.TagCreator.body;
import static j2html.TagCreator.h1;
/**
 * SpringMVC Controller that lives on the server side and handles incoming HTTP requests. It is basically a servlet but
 * using the power of SpringMVC we can avoid a lot of the raw servlet and request/response mapping uglies that
 * servlets require and instead just deal with simple, clean Java Objects. For more information on SpringMVC see:
 * http://static.springsource.org/spring/docs/current/spring-framework-reference/html/mvc.html
 */
@Controller
public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

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
        return new ResponseMessage(message, 201);
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

        String html = body().with(
                h1("WELCOME TO OUR PRITTY DEMO!").withClass("example")
        ).render();

        Document doc = Jsoup.parseBodyFragment(html);

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

    private void changeColorOfHeader(String objectColor){
        // TODO
    }

    private void changeColorOfFooter(String objectColor){
        // TODO
    }
}
