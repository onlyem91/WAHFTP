package nl.hackergames.pizza.common;

/**
 * Data transfer object (DTO) for transferring data from the server to the client. This object encapsulates the response
 * from the server when the user triggers the sayHello REST method. Using Spring's REST support (with Jackson XML
 * mapping under the covers) this bean will be automatically converted into XML and back again when a REST request is
 * made. This means we never have to worry about working directly with the nasty, type-less XML and can do all our
 * work based on this bean.
 * <p/>
 * Note using a bean for the simple example of saying hello is a bit of overkill. We could have gotten away with using
 * a String, however that would not of have demonstrated how to use a bean which is far more interesting. This example
 * gives you a starting point for you to add your own beans and extend to fit your needs.
 */
public class ResponseMessage {

    private String message;
    private int responseCode;

    public ResponseMessage() {
    }

    public ResponseMessage(String message, int responseCode) {
        this.message = message;
        this.responseCode = responseCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCreatedOn() {
        return responseCode;
    }

    public void setCreatedOn(int responseCode) {
        this.responseCode = responseCode;
    }
}
