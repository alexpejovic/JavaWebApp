package modules.main;

import modules.controllers.ConfBuild;
import modules.controllers.LoginController;
import org.apache.log4j.BasicConfigurator;
import org.json.simple.JSONObject;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Routes {
    static ConfBuild confBuild = new ConfBuild();
    static Map<String, Object> model = new HashMap<>();
    static String templatePath = "src/main/resources/web/endpoints/";
    static Status status = new Status();

    public static void main(String[] args) {

        BasicConfigurator.configure();
        staticFileLocation("/web");
        port(8000);

        get("/home", (req, res) -> {
            if (req.session().attribute("currentUser") == null) {
                res.redirect("/login"); return "";
            }
            return render(model, templatePath + "signin.html");
        });

        get("/login", (req, res) -> {
            res.redirect("/endpoints/login.html");
            return "";
        });

        get("/logout", (req, res) -> {
           req.session().removeAttribute("currentUser");
           status.setStatus("logout");
           status.clearMessage();
           res.redirect("/home"); return "";
        });

        get("/getstatus", (req, res) -> {
            return status.toJSON();
        });

        post("/signin", Routes::handleLogin);
    }

    private static String render(Map<String, Object> model, String templatePath) {
        System.out.println("rendering");
        return new MustacheTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    private static String handleLogin(Request request, Response response) {
        LoginController lc = confBuild.getLoginController();
        boolean login = lc.logIn(request.queryParams("user"), request.queryParams("pass"));
        if (login) {
            String curUser = lc.getLoggedUser();
            request.session().attribute("currentUser", curUser);
            model.put("user", curUser);
            response.redirect("/home");
        }
        JSONObject stat = new JSONObject();
        status.setStatus("error");
        status.setMessage("Invalid username or password");
        response.redirect("/home");
        return "";
    }

//    private static Map<String, Object> fetchData(String userType, String userID) {
//        if (userType.equals("organizer")) {
//            OrganizerController controller = confBuild.getOrgController(userID);
//        }
//    }
}
