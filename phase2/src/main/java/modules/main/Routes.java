package modules.main;

import org.apache.log4j.BasicConfigurator;
import static spark.Spark.*;

public class Routes {
    public static void main(String[] args) {
        BasicConfigurator.configure();
        staticFileLocation("/web");
        port(8000);
        get("/home", (req, res) -> {res.redirect("/endpoints/login.html"); return "";});
        get("/fetch", (req, res) -> {
            return "{\"message\": \"So long and thanks for all the fish\"}";
        });
        post("/signup", (req, res) -> {
            return "<p>You entered:</p><p>name: " + req.queryParams("name")
                    + "</p><p>password: " + req.queryParams("pass")
                    + "</p><p>email: " + req.queryParams("email") + "</p>";
        });
        post("/signin", (req, res) -> {
            return "<p>You entered:</p><p>username: " + req.queryParams("user")
                    + "</p><p>password: " + req.queryParams("pass") + "</p>";
        });
    }
}
