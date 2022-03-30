import com.google.gson.JsonObject;

import static spark.Spark.*;
import java.lang.Math;

public class ServiceMath {

    public static void main(String... args) {

        port(getPort());

        options("/*", (request, response) -> {
            String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
            if (accessControlRequestHeaders != null) {
                response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
            }

            String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
            if (accessControlRequestMethod != null) {
                response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
            }

            return "OK";
        });

        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));

        get("/sin", (req, res) -> {
            res.type("application/json");
            String valueS = req.queryParams("value");
            double value = Double.parseDouble(valueS);
            System.out.println("Esta entrando aca1");
            try {
                //return mongoDB.createConexion("AREP", value);
                JsonObject p = new JsonObject();
                p.addProperty("operation", "sin");
                p.addProperty("input", value);
                p.addProperty("output", Math.sin(value));

                return p;
            } catch (NumberFormatException e) {
                return null;
            }

        });

        get("/acos", (req, res) -> {
            res.type("application/json");
            String valueS = req.queryParams("value");
            double value = Double.parseDouble(valueS);
            System.out.println("Esta entrando aca1");
            try {
                //return mongoDB.createConexion("AREP", value);
                JsonObject p = new JsonObject();
                p.addProperty("operation", "acos");
                p.addProperty("input", value);
                p.addProperty("output", Math.acos(value));

                return p;
            } catch (NumberFormatException e) {
                return null;
            }
        });
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4568; //returns default port if heroku-port isn't set
    }
}
