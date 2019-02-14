package cn.itcast.ssm.mapper;

import java.util.List;

import cn.itcast.ssm.po.SysPermission;

/**
 * 1、权限的数据访问层接口
 * 项目名称：shrio 
 * 类名称：SysPermissionMapperCustom
 * 开发者：Lenovo
 * 开发时间：2019年1月20日下午5:52:11
 */
public interface SysPermissionMapperCustom {
	
	//根据用户id查询菜单
	public List<SysPermission> findMenuListByUserId(String userid)throws Exception;
	//根据用户id查询权限url
	public List<SysPermission> findPermissionListByUserId(String userid)throws Exception;

}
