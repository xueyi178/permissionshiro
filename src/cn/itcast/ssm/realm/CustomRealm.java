package cn.itcast.ssm.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.po.SysPermission;
import cn.itcast.ssm.service.SysService;

/**
 * 1、自定义的Realm
 * 项目名称：shrio_test 
 * 类名称：CustomRealm
 * 开发者：Lenovo
 * 开发时间：2019年2月13日下午3:08:19
 */
public class CustomRealm extends AuthorizingRealm{
	
	@Autowired
	private SysService sysService;

	/**
	 * 设置raelm的名称
	 */
	@Override
	public void setName(String name) {
		super.setName("customRealm");
	}


	/**
	 * 1、用于认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		//1、第一步从token中取出用户的信息
		String userCode = (String) token.getPrincipal();
		
		//2、第二步根据用户输入的userCode账号从数据库进行查询
		
		//如果查询不到返回null
		//模拟数据库中的账号是zhangsansan
		/*if(!userCode.equals("zhangsansan")) {
			return null;
		}*/
		
		//模拟从数据库中进行查询
		String password = "111111";
		
		//3、如果查询不到返回null
		
		//模拟静态数据
		//ActiveUser就是用户的身份的信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid("zhangsan");
		activeUser.setUsercode("zhangsan");
		activeUser.setUsername("张三");
		//根据用户id取出菜单
		List<SysPermission> menus = null;
		try {
			menus = sysService.findMenuListByUserId("zhangsan");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//将用户的菜单设置到activeUser中
		activeUser.setMenus(menus);
		
		
		//4、如果查询到就返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password, this.getName());
		
		return simpleAuthenticationInfo;
	}

	
	/**
	 * 2、用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//1、从principals里面获取主身份信息,
		//将getPrimaryPrincipal方法返回值转换为真实身份类型(在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo的身份信息类型)
		String userCode = (String) principals.getPrimaryPrincipal();
		
		//2、根据身份信息去获取权限信息
		//连接数据库
		//模拟从数据库获取数据
		List<String> permissions = new ArrayList<String>();
		permissions.clear();
		//用户的创建权限
		permissions.add("user:create");
		//商品的添加权限
		permissions.add("items:add");
		
		//3、查询权限数据,就返回授权信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到的授权信息填充到SimpleAuthorizationInfo对象中
		authorizationInfo.addStringPermissions(permissions);
			
		return authorizationInfo;
	}
}
