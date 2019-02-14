package cn.itcast.ssm.exception;

/**
 * 1、自定义异常类
 * 项目名称：shrio 
 * 类名称：CustomException
 * 开发者：Lenovo
 * 开发时间：2019年1月20日下午3:12:48
 */
public class CustomException extends Exception {
	
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -151231706849504359L;
	//异常信息
	private String message;
	
	public CustomException(String message){
		super(message);
		this.message = message;
		
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	

}
