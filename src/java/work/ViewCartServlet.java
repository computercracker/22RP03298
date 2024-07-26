package work;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/view-cart")
public class ViewCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // HTML content starts here
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>View Cart</title>");
        out.println("<style>");
        out.println("body { font-family: Arial, sans-serif; background-color: #f4f4f9; margin: 0; padding: 0; display: flex; justify-content: center; align-items: center; height: 100vh; }");
        out.println(".container { background: #fff; padding: 20px; border-radius: 8px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); text-align: center; max-width: 600px; width: 100%; }");
        out.println("h1 { color: #4CAF50; margin-bottom: 20px; }");
        out.println("p { margin: 10px 0; }");
        out.println("form { margin: 10px 0; }");
        out.println("button { background-color: #f44336; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; font-size: 1em; transition: background-color 0.3s; }");
        out.println("button:hover { background-color: #d32f2f; }");
        out.println("a { display: block; margin-top: 20px; text-decoration: none; color: #4CAF50; font-size: 1em; transition: color 0.3s; }");
        out.println("a:hover { color: #45a049; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='container'>");
        out.println("<h1>My Cart</h1>");

        Cookie[] cookies = request.getCookies();
        boolean cartEmpty = true;
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("cart".equals(cookie.getName())) {
                    ArrayList<String> cartItems = new ArrayList<>(Arrays.asList(URLDecoder.decode(cookie.getValue(), "UTF-8").split(",")));
                    for (String item : cartItems) {
                        out.println("<p>" + item + "</p>");
                        out.println("<form action='remove-item' method='post'>");
                        out.println("<input type='hidden' name='item' value='" + item + "'>");
                        out.println("<button type='submit'>Remove</button>");
                        out.println("</form>");
                    }
                    cartEmpty = false;
                }
            }
        }
        if (cartEmpty) {
            out.println("<p>My cart is empty</p>");
        }
        out.println("<a href='index.html'>Continue Shopping</a>");
        out.println("<form action='clear-cart' method='post'>");
        out.println("<button type='submit'>Clear Cart</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }
}
