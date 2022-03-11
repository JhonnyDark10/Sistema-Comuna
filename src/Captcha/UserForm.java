package Captcha;

import model.SegUsuario;

public class UserForm {
    private RandomStringGenerator rsg = new RandomStringGenerator(4);
 
    private SegUsuario user = new SegUsuario();
    private String captcha = rsg.getRandomString(), captchaInput;
 
    
      

	public SegUsuario getUser() {
		return user;
	}

	public void setUser(SegUsuario user) {
		this.user = user;
	}

	public String getCaptcha() {
        return captcha;
    }
 
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
 
    public String getCaptchaInput() {
        return captchaInput;
    }
 
    public void setCaptchaInput(String captchaInput) {
        this.captchaInput = captchaInput;
    }
     
    public void regenerateCaptcha() {
        this.captcha = rsg.getRandomString();
    }
 
}