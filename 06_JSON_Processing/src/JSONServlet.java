import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Helitha Sri
 * @created 4/25/2022 - 10:23 AM
 * @project JavaEE
 */

@WebServlet(urlPatterns = "/json")
public class JSONServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        /*JsonObjectBuilder objB = Json.createObjectBuilder();
        objB.add("id","C001");
        objB.add("name","Helitha Sri");
        objB.add("salary",15000.00);
        JsonObject build = objB.build();

        PrintWriter writer = resp.getWriter();
        writer.print(build);*/

        JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

        JsonObjectBuilder objB = Json.createObjectBuilder();
        objB.add("id","C001");
        objB.add("name","Helitha Sri");
        objB.add("salary",15000.00);

        JsonObjectBuilder objB2 = Json.createObjectBuilder();
        objB2.add("id","C002");
        objB2.add("name","Praveen");
        objB2.add("salary",5000.00);

        arrayBuilder.add(objB.build());
        arrayBuilder.add(objB2.build());

        PrintWriter writer = resp.getWriter();
        writer.print(arrayBuilder.build());


    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Invoked");

        /*ServletInputStream inputStream = req.getInputStream();
        int read;
        while ((read = inputStream.read())!=-1){
            System.out.print((char) read);
        }*/

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String id = jsonObject.getString("id");
        String name = jsonObject.getString("name");
        System.out.println(id);


    }
}
