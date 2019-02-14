package cn.itcast.ssm.service;

import java.util.List;

import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.po.SysPermission;
import cn.itcast.ssm.po.SysUser;

/**
 * 1、用户业务逻辑层接口
 * 项目名称：shrio 
 * 类名称：IUserService
 * 开发者：Lenovo
 * 开发时间：2019年1月20日下午2:57:35
 */
public interface SysService {

	/**
	 * 1、根据用户名和密码进行身份认证,如果认证通过,返回用户信息
	 * @param userCode
	 * @param passwrod
	 * @return
	 * @throws Exception
	 */
	public ActiveUser authenticat(String userCode,String passwrod)throws Exception;

	/**
	 * 2、根据账号查询用户信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysUser findSysUserByUserCode(String code)throws Exception;
	
	/**
	 * 3、根据用户id查询用户权限的菜单
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SysPermission> findMenuListByUserId(String userId)throws Exception;
	
	/**
	 * 4、根据用户id查询用户权限的url
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List<SysPermission> findPermissionListByUserId(String userId)throws Exception;
}
