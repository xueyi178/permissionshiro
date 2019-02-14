package cn.itcast.ssm.po;

import java.util.List;

/**
 * 1、用户信息
 * 项目名称：shrio 
 * 类名称：ActiveUser
 * 开发者：Lenovo
 * 开发时间：2019年1月20日下午2:36:01
 */
public class ActiveUser implements java.io.Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = 1698680017926203329L;
	private String userid;//用户id（主键）
	private String usercode;// 用户账号
	private String username;// 用户名称

	private List<SysPermission> menus;// 菜单
	private List<SysPermission> permissions;// 权限

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getUsercode() {
		return usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public List<SysPermission> getMenus() {
		return menus;
	}

	public void setMenus(List<SysPermission> menus) {
		this.menus = menus;
	}

	public List<SysPermission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<SysPermission> permissions) {
		this.permissions = permissions;
	}
}
