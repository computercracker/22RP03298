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

@WebServlet("/remove-item")
public class RemoveItemServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String item = request.getParameter("item");
        if (item != null && !item.isEmpty()) {
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cart".equals(cookie.getName())) {
                        ArrayList<String> cartItems = new ArrayList<>(Arrays.asList(URLDecoder.decode(cookie.getValue(), "UTF-8").split(",")));
                        cartItems.remove(item);
                        String cartValue = URLEncoder.encode(String.join(",", cartItems), "UTF-8");
                        if (cartItems.isEmpty()) {
                            cookie.setMaxAge(0);
                        } else {
                            cookie.setValue(cartValue);
                            cookie.setMaxAge(60 * 60 * 24);
                        }
                        response.addCookie(cookie);
                        break;
                    }
                }
            }
        }
        response.sendRedirect("view-cart.html");
    }
}
