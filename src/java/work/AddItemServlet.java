package work;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

@WebServlet("/add-item")
public class AddItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String item = request.getParameter("item");
        if (item != null && !item.isEmpty()) {
            item = URLEncoder.encode(item, "UTF-8");
            Cookie[] cookies = request.getCookies();
            ArrayList<String> cartItems = new ArrayList<>();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cart".equals(cookie.getName())) {
                        cartItems = new ArrayList<>(Arrays.asList(URLDecoder.decode(cookie.getValue(), "UTF-8").split(",")));
                        break;
                    }
                }
            }
            cartItems.add(item);
            String cartValue = URLEncoder.encode(String.join(",", cartItems), "UTF-8");
            Cookie cartCookie = new Cookie("cart", cartValue);
            cartCookie.setMaxAge(60 * 60 * 24);
            response.addCookie(cartCookie);
        }
        response.sendRedirect("view-cart.html");
    }
}
