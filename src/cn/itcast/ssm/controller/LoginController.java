package cn.itcast.ssm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.service.SysService;

/**
 * 1、用户的登录和退出
 * 项目名称：shrio 
 * 类名称：LoginController
 * 开发者：Lenovo
 * 开发时间：2019年1月20日下午3:24:05
 */
@Controller
public class LoginController {
	
	@Autowired
	private SysService userService;
	
	
	//用户登陆提交方法
	/**
	 * 
	 * <p>Title: login</p>
	 * <p>Description: </p>
	 * @param session
	 * @param randomcode 输入的验证码
	 * @param usercode 用户账号
	 * @param password 用户密码 
	 * @return
	 * @throws Exception
	 * 没有使用shiro之前的表单登录
	@RequestMapping("/login")
	public String login(HttpSession session, String randomcode,String usercode,String password)throws Exception{
		
		//校验验证码，防止恶性攻击
		//从session获取正确验证码
		String validateCode = (String) session.getAttribute("validateCode");
		
		//输入的验证和session中的验证进行对比 
		if(!randomcode.equals(validateCode)){
			//抛出异常
			throw new CustomException("验证码输入错误");
		}
		
		//调用service校验用户账号和密码的正确性
		ActiveUser activeUser = userService.authenticat(usercode, password);
		//如果service校验通过，将用户身份记录到session
		session.setAttribute("activeUser", activeUser);

		//重定向到商品查询页面
		return "redirect:/first.action";
	}*/
	
	/**
	 * shiro登录的地址,登录提交的地址和applicationContext-shiro.xml中配置的loginUrl一致
	 * @return
	 */
	@RequestMapping("/login")
	public String login(HttpServletRequest request) throws Exception {
		//1、如果登录失败从request中获取认证的异常信息,shiroLoginFailure就是shiro异常类的全限定名
		String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");

		//2、根据shiro返回的异常类路径来进行判断,抛出指定的异常信息
		if(exceptionClassName!=null){
			if (UnknownAccountException.class.getName().equals(exceptionClassName)) {
				//3、最终会抛给异常处理器
				throw new CustomException("账号不存在");
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				throw new CustomException("用户名/密码错误");
			} else{
				throw new Exception();//最终在异常处理器生成未知错误
			}
		}
		//此方法不处理登录成功(认证成功),shiro认证成功后会自动 跳转到上一个路径
		//登录失败还返回到login的页面
		return "login";
	}
	
	/*//用户退出
	@RequestMapping("/logout")
	public String logout(HttpSession session)throws Exception{
		//session失效
		session.invalidate();
		//重定向到商品查询页面
		return "redirect:/first.action";
		
	}*/
}
