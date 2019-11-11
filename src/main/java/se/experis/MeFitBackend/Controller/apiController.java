package se.experis.MeFitBackend.Controller;

import org.json.JSONException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;



@Controller
@Component
public class apiController {


    @RequestMapping(value="/public/helloworld" , method = RequestMethod.GET , produces = "application/json")
    @ResponseBody
    public String greet() {
        return "helloworld from public page!";
    }


    @RequestMapping(value="/private/helloworld" , method = RequestMethod.GET , produces = "application/json")
    @ResponseBody
    public String greets() {
        return "helloworld from private secured page!";
    }
    @RequestMapping(value = "/api/public", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String publicEndpoint() throws JSONException {
        return new JSONObject()
                .put("message", "Hello from a public endpoint! You don\'t need to be authenticated to see this.")
                .toString();
    }

    @RequestMapping(value = "/api/private", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String privateEndpoint() throws JSONException {
        return new JSONObject()
                .put("message", "Hello from a private endpoint! You need to be authenticated to see this.")
                .toString();
    }

    @RequestMapping(value = "/api/private-scoped", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public String privateScopedEndpoint() throws JSONException {
        return new JSONObject()
                .put("message", "Hello from a private endpoint! You need to be authenticated and have a scope of read:messages to see this.")
                .toString();
    }
}