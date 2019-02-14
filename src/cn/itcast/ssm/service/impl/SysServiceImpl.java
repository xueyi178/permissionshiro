package cn.itcast.ssm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.ssm.exception.CustomException;
import cn.itcast.ssm.mapper.SysPermissionMapperCustom;
import cn.itcast.ssm.mapper.SysUserMapper;
import cn.itcast.ssm.po.ActiveUser;
import cn.itcast.ssm.po.SysPermission;
import cn.itcast.ssm.po.SysUser;
import cn.itcast.ssm.po.SysUserExample;
import cn.itcast.ssm.po.SysUserExample.Criteria;
import cn.itcast.ssm.service.SysService;
import cn.itcast.ssm.util.MD5;
/**
 * 1、用户的业务逻辑层实现类
 * 项目名称：shrio 
 * 类名称：UserServiceImpl
 * 开发者：Lenovo
 * 开发时间：2019年1月20日下午2:59:47
 */
public class SysServiceImpl implements SysService {

	@Autowired
	private SysUserMapper sysUserMapper;
	
	@Autowired
	private SysPermissionMapperCustom sysPermissionMapperCustom;
	
	/**
	 * 1、根据用户名和密码进行身份认证,如果认证通过,返回用户信息
	 * @param userCode
	 * @param passwrod
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActiveUser authenticat(String userCode, String passwrod) throws Exception {
		SysUser sysUser = this.findSysUserByUserCode(userCode);
		if(null == sysUser) {
			//抛出异常信息
			throw new CustomException("用户账号不存在");
		}
		
		//数据库的密码
		String passworddb = sysUser.getPassword();
		//页面输入的密码
		String input_md5 = new MD5().getMD5ofStr(passwrod);
		
		//equalsIgnoreCase：忽略大小写
		if(!input_md5.equalsIgnoreCase(passworddb)) {
			//抛出异常信息
			throw new CustomException("用户账号或密码错误");
		}
		
		//根据用户id查询菜单
		List<SysPermission> menuListByUserId = this.findMenuListByUserId(sysUser.getId());
		
		//根据用户id查询url
		List<SysPermission> permissionListByUserId = this.findPermissionListByUserId(sysUser.getId());
		
		
		ActiveUser activeUser = new ActiveUser();
		activeUser.setUserid(sysUser.getId());
		//用户账号
		activeUser.setUsercode(userCode);
		//用户名称
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setMenus(menuListByUserId);
		activeUser.setPermissions(permissionListByUserId);
		return activeUser;
	}
	
	/**
	 * 2、根据账号查询用户信息
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public SysUser findSysUserByUserCode(String code) throws Exception {
		SysUserExample example = new SysUserExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUsercodeEqualTo(code);
		List<SysUser> list = sysUserMapper.selectByExample(example);
		if(null != list && list.size() == 1) {
			return list.get(0);
		}
		return null;
	}

	/**
	 * 3、根据用户id查询用户权限的菜单
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysPermission> findMenuListByUserId(String userId) throws Exception {
		return sysPermissionMapperCustom.findMenuListByUserId(userId);
	}

	/**
	 * 4、根据用户id查询用户权限的url
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<SysPermission> findPermissionListByUserId(String userId) throws Exception {
		return sysPermissionMapperCustom.findPermissionListByUserId(userId);
	}
}