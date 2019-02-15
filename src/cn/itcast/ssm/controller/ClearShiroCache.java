package cn.itcast.ssm.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.itcast.ssm.realm.CustomRealm;
/**
 * 1、手动调用controller来清除shiro的缓存数据
 * 项目名称：permission_shiro1110 
 * 类名称：ClearShiroCache
 * 开发者：Lenovo
 * 开发时间：2019年2月15日下午3:25:59
 */
@Controller
public class ClearShiroCache {
	
	@Autowired
	private CustomRealm customRealm;

	/**
	 * 1、清除缓存的操作
	 * @return
	 */
	@RequestMapping(value="/clearShiroCache")
	public String clearShiroCache() {
		//清除缓存,将来正常开发要在service调用customRealm.clearCached();
		customRealm.clearCached();
		return "SUCCESS";
	}
}
