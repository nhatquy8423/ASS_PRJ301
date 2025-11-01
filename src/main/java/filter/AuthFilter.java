package filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/cart.jsp", "/checkout.jsp"})
public class AuthFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Ép kiểu request/response để dùng session
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // Lấy session hiện tại
        HttpSession session = req.getSession(false); // false => không tạo mới nếu chưa có

        // Kiểm tra đăng nhập 
        Object customer = (session != null) ? session.getAttribute("customer") : null;

        if (customer == null) {
            // Nếu chưa đăng nhập => chuyển hướng đến trang login
            res.sendRedirect(req.getContextPath() + "/login.jsp");
        } else {
            // Nếu đã đăng nhập => cho phép đi tiếp
            chain.doFilter(request, response);
        }
    }
}
