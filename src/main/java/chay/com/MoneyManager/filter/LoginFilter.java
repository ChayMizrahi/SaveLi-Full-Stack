package chay.com.MoneyManager.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.util.UrlPathHelper;

import chay.com.MoneyManager.entities.CustomLogin;

@WebFilter("/moneyManager/*")
public class LoginFilter implements Filter {

	@Value("${money.manager.project.sessin.name}")
	private String attributeName;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession(false);
		String path = new UrlPathHelper().getPathWithinApplication(servletRequest);
		if (session == null) {
			servletResponse.sendError(401, "You are not logged in");
		} else {
			if (session.getAttribute(attributeName) != null) {
				String restPath = path.split("/")[2];
				CustomLogin customLogin = (CustomLogin) session.getAttribute(attributeName);
				if (restPath.toUpperCase().equals(customLogin.getLoginType().toString())) {
					chain.doFilter(request, response);
				} else {
					servletResponse.sendError(401, "You are not logged in");
				}
			} else {
				servletResponse.sendError(401, "You are not logged in");
			}
		}

	}

}
