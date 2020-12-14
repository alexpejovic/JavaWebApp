package modules.main;

import modules.controllers.*;
import modules.presenters.Model;
import org.apache.log4j.BasicConfigurator;
import spark.ModelAndView;
import spark.Request;
import spark.Response;
import spark.template.mustache.MustacheTemplateEngine;

import java.time.LocalDateTime;
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
//    static SpeakerController spkController;
    static Attendable attendableController;
    static Messageable messageableController;

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
           model.clear(true);
           model.setErrorStatus(true, "logout");
           res.redirect("/home"); return "";
        });

        get("/getmodel", (req, res) -> model.toJSON());

        post("/signin", Routes::handleLogin);
        post("/sendmessage", Routes::handleMessage);
        post("/unattendevent", Routes::unattendEvent);
        post("/attendevent", Routes::attendEvent);
        post("/cancelevent", Routes::cancelEvent);
        post("/addfriend", Routes::addFriend);
        post("/createevent", Routes::createEvent);
        post("/archivemessage", (req, res) -> "Archive Message");
        post("/deletemessage", (req, res) -> "Delete Message");
    }

    private static String attendEvent(Request request, Response response) {
        attendableController.attendEvent(request.queryParams("event"));
        updateModel(false);
        response.redirect("/home");
        return "";
    }

    private static String unattendEvent(Request request, Response response) {
        attendableController.cancelEnrollment(request.queryParams("event"));
        updateModel(false);
        response.redirect("/home");
        return "";
    }

    private static String cancelEvent(Request request, Response response) {
        // The request for this method can only be sent if the user is an organizer so no check is needed
        orgController.cancelEvent(request.queryParams("event"));
        updateModel(false);
        response.redirect("/home");
        return "";
    }

    private static String createEvent(Request request, Response response) {
        String[] dateTime = request.queryParams("date").split(" ");
        String[] date = dateTime[0].split("/");
        String[] time = dateTime[1].split(":");
        int day = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int year = Integer.parseInt(date[2]);
        int hour = Integer.parseInt(time[0]);
        int minute = Integer.parseInt(time[1]);
        LocalDateTime startTime = LocalDateTime.of(year, month, day, hour, minute);
        LocalDateTime endTime = startTime.plusHours(1);
        int capacity = Integer.parseInt(request.queryParams("capacity"));
        boolean isVIP = request.queryParams("vip").equals("on");
        orgController.scheduleEvent(request.queryParams("roomNum"), startTime, endTime,
                request.queryParams("name"), capacity, isVIP);

        updateModel(false);
        response.redirect("/home");
        return "";
    }

    private static String addFriend(Request request, Response response) {
        attController.addUserToFriendList(request.queryParams("friendId"));
        updateModel(false);
        response.redirect("/home");
        return "";
    }

    private static String handleMessage(Request request, Response response) {
        messageableController.sendMessage(request.queryParams("recipient"), request.queryParams("reply"));
        updateModel(false);
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
            initController();
            response.redirect("/home");
            return "";
        }
        model.setErrorStatus(false, "Invalid username or password");
        response.redirect("/home");
        return "";
    }

    private static void initController() {
        if (userType.equals("attendee")) {
            attController = confBuild.getAttController(currentUser);
            attendableController = attController;
            messageableController = attController;
            updateModel(false);
        }
        else if (userType.equals("organizer")) {
            orgController = confBuild.getOrgController(currentUser);
            attendableController = orgController;
            messageableController = orgController;
            updateModel(false);
        }
        else if (userType.equals("speaker")) {
            messageableController = confBuild.getSpkController(currentUser);
            updateModel(false);
        }
    }

    private static void updateModel(boolean all) {
        model.clear(all);
        messageableController.updateModel();
    }

    private static String render(Map<String, Object> model, String templatePath) {
        return new MustacheTemplateEngine().render(new ModelAndView(model, templatePath));
    }
}
