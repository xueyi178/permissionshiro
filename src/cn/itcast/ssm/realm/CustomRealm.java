package cn.itcast.ssm.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.po.SysPermission;
import cn.itcast.ssm.po.SysUser;
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
	 * 1、realm的认证方法,来进行用于认证
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
			throws AuthenticationException {
		//1、第一步从token中取出用户的信息
		//2、token中需要的用户名和密码,这里只需要用户名
		String userCode = (String) token.getPrincipal();
		
		//2、第二步根据用户输入的userCode账号从数据库进行查询
		SysUser sysUser = null;
		try {
			sysUser = sysService.findSysUserByUserCode(userCode);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		//判断用户是否为空
		if(null == sysUser) {
			return null;
		}
		
		//从数据库中查询出密码
		String password = sysUser.getPassword();
		
		//从数据库中查询出盐
		String salt = sysUser.getSalt();
		//3、如果查询不到返回null
		
		//模拟静态数据
		//ActiveUser就是用户的身份的信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		//根据用户id取出菜单
		List<SysPermission> menus = null;
		try {
			menus = sysService.findMenuListByUserId(sysUser.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//将用户的菜单设置到activeUser中
		activeUser.setMenus(menus);
		
		//4、如果查询到就返回认证信息AuthenticationInfo
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(activeUser, password, ByteSource.Util.bytes(salt), this.getName());
		
		return simpleAuthenticationInfo;
	}

	
	/**
	 * 2、用户授权
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("执行了缓存 888888888888888888");
		//1、从principals里面获取主身份信息,
		//将getPrimaryPrincipal方法返回值转换为真实身份类型(在上边的doGetAuthenticationInfo认证通过填充到SimpleAuthenticationInfo的身份信息类型)
		ActiveUser activeUser =  (ActiveUser) principals.getPrimaryPrincipal();
		
		//2、根据身份信息去获取权限信息
		//连接数据库
		//模拟从数据库获取数据
		List<SysPermission> findPermissionListByUserId = null;
		try {
			findPermissionListByUserId = sysService.findPermissionListByUserId(activeUser.getUserid());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//创建一个String的list集合对象
		List<String> permissions = new ArrayList<>();
		for (SysPermission sysPermission : findPermissionListByUserId) {
			//将数据库的权限标识符号,放到集合当中
			permissions.add(sysPermission.getPercode());
		}
		
		//3、查询权限数据,就返回授权信息
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//将上边查询到的授权信息填充到SimpleAuthorizationInfo对象中
		authorizationInfo.addStringPermissions(permissions);
			
		return authorizationInfo;
	}
	
	/**
	 * 清除缓存
	 */
	public void clearCached() {
		PrincipalCollection principals = SecurityUtils.getSubject().getPrincipals();
		super.clearCache(principals);
	}
}
