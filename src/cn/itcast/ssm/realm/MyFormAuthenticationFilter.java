package cn.itcast.ssm.realm;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

/**
 * 1、自定义MyFormAuthenticationFilter,认证之前来实现验证码的校验
 * 项目名称：permission_shiro1110 
 * 类名称：MyFormAuthenticationFilter
 * 开发者：Lenovo
 * 开发时间：2019年2月15日下午3:46:47
 */
public class MyFormAuthenticationFilter extends FormAuthenticationFilter{

	/**
	 * 1、原FormAuthenticationFilter的验证方法
	 */
	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		System.out.println("进入了验证码页面");
		//1、在这里来进行验证码的校验
		//校验验证码，防止恶性攻击
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		HttpSession session = httpServletRequest.getSession();
		//从session取出正确验证码
		String validateCode = (String) session.getAttribute("validateCode");

		//取出页面的验证码
		String randomcode = request.getParameter("randomcode");
		
		//输入的验证和session中的验证进行对比 
		if(null != randomcode && null != validateCode && !randomcode.equals(validateCode)){
			//如果校验失败,将验证码的错误失败信息,通过shiroLoginFailure设置到request中
			httpServletRequest.setAttribute("shiroLoginFailure", "randomCodeError");
			//拒绝访问,不再校验账号和密码
			return true;
		}
		return super.onAccessDenied(request, response);
	}
}