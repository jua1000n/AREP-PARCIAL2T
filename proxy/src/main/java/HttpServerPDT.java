
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Locale;

public class HttpServerPDT {

    private static Integer port;
    private static Integer cont = 0;
    private static String listicaURL [] = new String [2];
    private static String url = "ec2-3-87-66-72.compute-1.amazonaws.com";
    private static Integer ports = 4567;

    public static void main(String[] args) throws IOException {
        listicaURL[0] = "ec2-54-210-250-199.compute-1.amazonaws.com:4568";
        listicaURL[1] = "ec2-44-202-237-209.compute-1.amazonaws.com:4568";

        ServerSocket serverSocket = null;
        try {
            port = getPort();
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
            System.exit(1);
        }
        Socket clientSocket = null;

        boolean running = true;


        while(running) {
            try {
                System.out.println("Listo para recibir ...");
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String inputLine, outputLine;

            boolean primerLinea = true;
            String file = "";

            while ((inputLine = in.readLine()) != null) {
                System.out.println("Received: " + inputLine);
                if (primerLinea) {
                    file = inputLine.split(" ")[1];
                    System.out.println("File" + file);
                    primerLinea = false;
                }
                if (!in.ready()) {
                    break;
                }
            }
            if (file.startsWith("/math")) {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "<label>Cadena:</label>\n"
                        + "<input name=\"name\">\n"
                        + "<button class=\"button\" onclick=\"inputValuesAcos()\">acos</button>\n"
                        + "<button class=\"button\" onclick=\"inputValuesSin()\">sin</button>\n"
                        + "<script>\n"
                        + "let numberc;\n"
                        + "let res1 = \"\";\n"
                        + "function inputValuesAcos() {\n" +
                        "    const url = '';\n" +
                        "    numberc = document.getElementsByName(\"name\")[0].value;\n" +
                        "    console.log(numberc);\n" +
                        //"    const url1 = `http://localhost:"+4567+"/acos?value=${numberc}`;\n" +
                        "    const url1 = `http://"+url+":"+ports+"/acos?cadena=${numberc}`;\n" +
                        "\n" +
                        "    getapi(url1);\n" +
                        "}" +
                        "function inputValuesSin() {\n" +
                        "    const url = '';\n" +
                        "    numberc = document.getElementsByName(\"name\")[0].value;\n" +
                        "    console.log(numberc);\n" +
                        "\n" +
                        //"    const url1 = `http://localhost:"+4567+"/sin?value=${numberc}`;\n" +
                        "    const url1 = `http://"+"url"+":"+ports+"/sin?cadena=${numberc}`;\n" +
                        "\n" +
                        "    getapi(url1);\n" +
                        "}"
                        + "async function getapi(url1){"
                        + "console.log(url1);"
                        + " const response = await fetch(url1, {method: 'GET', headers: {'Content-Type': 'application/json'}});\n"
                        + " let data = await response.json();"
                        + " res1 = data;"
                        + "console.log(res1);"
                        +" var x = document.getElementById(\"resultt\");\n" +
                        "    x.querySelector(\".example\").innerHTML = (JSON.stringify(res1));\n"
                        + "}"

                        + "</script>\n"
                        + "<div id=\"resultt\">\n" +
                        "                <p class=\"example\"></p>\n" +
                        "            </div"
                        + "</body>"
                        + "</html>";

            }else if(file.startsWith("/acos")) {
                String res = "";
                try {
                    String nameC = file.toLowerCase(Locale.ROOT).split("=")[1];
                    res = getJSONClima(nameC, "acos");
                    System.out.println(res);
                    System.out.println(nameC + " sssssss");
                }catch (Exception e){

                }

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + res;
            }else if(file.startsWith("/sin")) {
                String res = "";
                try {
                    String nameC = file.toLowerCase(Locale.ROOT).split("=")[1];
                    res = getJSONClima(nameC, "sin");
                    System.out.println(res);
                    System.out.println(nameC + " sssssss");
                }catch (Exception e){

                }

                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: application/json\r\n"
                        + "\r\n"
                        + res;
            }
            else {
                outputLine = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n"
                        + "\r\n"
                        + "<!DOCTYPE html>"
                        + "<html>"
                        + "<head>"
                        + "<meta charset=\"UTF-8\">"
                        + "<title>Title of the document</title>\n"
                        + "</head>"
                        + "<body>"
                        + "My Web Site"
                        + "</body>"
                        + "</html>";
            }
            out.println(outputLine);

            out.close();
            in.close();
            clientSocket.close();
        }
        serverSocket.close();
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set
    }

    private static String getJSONClima(String integer, String type) {
        String res = "{}";
        URL url = null;
        try {
            System.out.println(listicaURL[cont]);
            url = new URL("http://"+ listicaURL[cont]+"/"+ type +"?value="+ integer);
            if(cont == 1) {
                cont = 0;
            }else {
                cont++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (
                BufferedReader reader= new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                res = inputLine;
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return res;
        //return null;
    }
}
