package org.symagic.user.action.login;

import org.symagic.common.service.UserService;
import org.symagic.common.utilty.captcha.JCaptcha;
import org.symagic.common.utilty.session.SessionUtilty;
import org.symagic.user.utilty.UserSessionUtilty;

import com.opensymphony.xwork2.ActionSupport;

public class LoginAction extends ActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2935337682932323036L;
	//传入
	private String password;//登录密码
	private String captchaValue;//验证码
	private String userName;//登录用户名
	
	private String toURL;//记录登录前的页面
	//配置项
	private JCaptcha symagicCaptcha;//用于检验验证码是否正确
    private UserService userService;
    //传出
	private boolean loginResult;//登录是否成功
	private String resultInfo;
	//内部变量
	private boolean validateResult=true;
	
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		if(UserSessionUtilty.isLogin()){
			resultInfo="已登录";
			loginResult=false;
			return SUCCESS;
		}
		if(!validateResult){
			loginResult=false;
			return SUCCESS;
		}
		loginResult=userService.login(userName, password);
		if(!loginResult)
		{
			resultInfo="登录失败";
		}
		else{
			resultInfo="登录成功";
		}
		
		return super.execute();
	}
	
	@Override
	public void validate() {
		// TODO Auto-generated method stub
		//用户名和密码的验证
		if(isEmpty(userName)||isEmpty(password)){
			validateResult=false;
			resultInfo="用户名和密码不能为空";
			return;
		}
		if(userName.indexOf("@")==-1){
			validateResult=false;
			resultInfo="用户名格式不对";
			return;
		}
		//登录三次失败后，验证码的检验
		Integer errorTimes=UserSessionUtilty.getLoginErrorTime();
		if(errorTimes!=null&&errorTimes>=3){
			if(isEmpty(captchaValue)){
				validateResult=false;
				resultInfo="验证码为空";
				return;
			}
			validateResult=symagicCaptcha.validateCaptcha(SessionUtilty.getSessionID(), captchaValue);
			if(validateResult==false){
				resultInfo="验证码错误";
			}
			return;
			
		}
		
		super.validate();
	}
	
	private boolean isEmpty(String content){
		return (content==null||content.trim().equals(""));
	}
	
	
	public JCaptcha getSymagicCaptcha() {
		return symagicCaptcha;
	}
	public void setSymagicCaptcha(JCaptcha symagicCaptcha) {
		this.symagicCaptcha = symagicCaptcha;
	}
	public UserService getUserService() {
		return userService;
	}
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	
	
	public boolean isLoginResult() {
		return loginResult;
	}
	public void setLoginResult(boolean loginResult) {
		this.loginResult = loginResult;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCaptchaValue() {
		return captchaValue;
	}
	public void setCaptchaValue(String captchaValue) {
		this.captchaValue = captchaValue;
	}
	
	
	public String getResultInfo() {
		return resultInfo;
	}

	public void setResultInfo(String resultInfo) {
		this.resultInfo = resultInfo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getToURL() {
		return toURL;
	}
	public void setToURL(String toURL) {
		this.toURL = toURL;
	}

}
