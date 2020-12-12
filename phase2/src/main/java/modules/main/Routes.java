package modules.main;

import modules.controllers.*;
import modules.presenters.Model;
import org.apache.log4j.BasicConfigurator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;

public class Routes {
    static Map<String, Object> mdl = new HashMap<>();
    static String templatePath = "src/main/resources/web/endpoints/";
    static Model model = new Model();
    static ConfBuild confBuild = new ConfBuild(model);

    static String currentUser;
    static String userType;

    static OrganizerController orgController;
    static AttendeeController attController;
    static SpeakerController spkController;

    public static void main(String[] args) {

        BasicConfigurator.configure();
        staticFileLocation("/web");
        port(8000);

        get("/home", (req, res) -> {
            if (req.session().attribute("currentUser") == null) {
                res.redirect("/login"); return "";
            }
            return render(mdl, templatePath + "signin.html");
        });

        get("/login", (req, res) -> {
            res.redirect("/endpoints/login.html");
            return "";
        });

        get("/logout", (req, res) -> {
           req.session().removeAttribute("currentUser");
           model.clear();
           model.setErrorStatus(true, "logout");
           res.redirect("/home"); return "";
        });

        get("/getmodel", (req, res) -> {
            return model.toJSON();
        });

        post("/signin", Routes::handleLogin);

        post("/sendreply", Routes::handleReply);
    }

    private static String render(Map<String, Object> model, String templatePath) {
        System.out.println("rendering");
        return new MustacheTemplateEngine().render(new ModelAndView(model, templatePath));
    }

    private static String handleReply(Request request, Response response) {
        if (userType.equals("organizer")) {
            orgController = confBuild.getOrgController(currentUser);
            orgController.sendMessage(request.queryParams("recipient"), request.queryParams("reply"));
            orgController.updateModel();
        }
        else if (userType.equals("attendee")) {
            attController = confBuild.getAttController(currentUser);
            attController.sendMessage(request.queryParams("recipient"), request.queryParams("reply"));
            attController.updateModel();
        }
        else if (userType.equals("speaker")) {
            spkController = confBuild.getSpkController(currentUser);
            spkController.sendMessage(request.queryParams("recipient"), request.queryParams("reply"));
            spkController.updateModel();
        }
        response.redirect("/home");
        return "";
    }

    private static String handleLogin(Request request, Response response) {
        LoginController lc = confBuild.getLoginController();
        boolean login = lc.logIn(request.queryParams("user"), request.queryParams("pass"));
        if (login) {
            currentUser = lc.getLoggedUser();
            userType = lc.getUserType();
            request.session().attribute("currentUser", currentUser);
            mdl.put("user", currentUser);
            updateModel();
            response.redirect("/home");
            return "";
        }
        model.setErrorStatus(false, "Invalid username or password");
        response.redirect("/home");
        return "";
    }

    private static void updateModel() {
        System.out.println("fetching");
        System.out.println(userType);
        if (userType.equals("organizer")) {
            orgController = confBuild.getOrgController(currentUser);
            orgController.updateModel();
        }
        else if (userType.equals("attendee")) {
            attController = confBuild.getAttController(currentUser);
            attController.updateModel();
        }
        else if (userType.equals("speaker")) {
            spkController = confBuild.getSpkController(currentUser);
            spkController.updateModel();
        }
    }
}
