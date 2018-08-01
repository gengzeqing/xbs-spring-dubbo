package com.xuebusi.xbs.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 头部过滤器
 * @author 
 */
public class HeaderFilter implements Filter {

	@Override
	public void destroy() {

	}

	/**
	 * 解决跨域 CORS 全称是"跨域资源共享"
 	 *实现Filter 接口重写doFilter方法
	 */
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest)req;
		HttpServletResponse response = (HttpServletResponse) res;
		/**
		 * CORS是一个W3C标准，全称是"跨域资源共享"
		 * 它允许浏览器向跨源服务器，发出XMLHttpRequest请求，从而克服了AJAX只能同源使用的限制，
		 * 整个CORS通信过程，都是浏览器自动完成，不需要用户参与。
		 * 浏览器将CORS请求分成两类：简单请求（simple request）和非简单请求（not-so-simple request）。
		 */
		String originHeader = request.getHeader("Origin");
		// 该字段是必须的。它的值要么是请求时Origin字段的值，要么是一个*，表示接受任意域名的请求。
		response.setHeader("Access-Control-Allow-Origin", originHeader);
		//该字段必需，它的值是逗号分隔的一个字符串，表明服务器支持的所有跨域请求的方法。
		//注意，返回的是所有支持的方法，而不单是浏览器请求的那个方法。这是为了避免多次"预检"请求。
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		//该字段可选，用来指定本次预检请求的有效期，单位为秒。
		//上面结果中，有效期是20天（1728000秒），即允许缓存该条回应1728000秒（即20天），在此期间，不用发出另一条预检请求。
		response.setHeader("Access-Control-Max-Age", "0");
		//如果浏览器请求包括Access-Control-Request-Headers字段，则Access-Control-Allow-Headers字段是必需的。
		//它也是一个逗号分隔的字符串，表明服务器支持的所有头信息字段，不限于浏览器在"预检"中请求的字段。( 可以添加额外属性 )
		response.setHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With");
		//该字段可选。它的值是一个布尔值，表示是否允许发送Cookie。默认情况下，Cookie不包括在CORS请求之中。
		//设为true，即表示服务器明确许可，Cookie可以包含在请求中，一起发给服务器。这个值也只能设为true，
		//如果服务器不要浏览器发送Cookie，删除该字段即可。
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("XDomainRequestAllowed","1");
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
}
