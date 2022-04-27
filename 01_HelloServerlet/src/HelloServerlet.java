import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author HelithaSri
 * @created 4/5/2022 - 4:35 PM
 * @project JavaEE
 */

@WebServlet(name = "MyServlet",urlPatterns = "/hello")
public class HelloServerlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("req eka awa");
        PrintWriter writer = resp.getWriter();
        writer.write("Respond Sent");
    }
}
