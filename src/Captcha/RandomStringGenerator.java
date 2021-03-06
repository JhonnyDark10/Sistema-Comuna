package Captcha;

import java.util.Random;

public class RandomStringGenerator {
	private int length;
	private String alphabet = "abcdefghijklmnopqrstuvwxyz";
	private final Random rn = new Random();
	
	public RandomStringGenerator(int length) {
		if(length <= 0)
			throw new IllegalArgumentException("La longitud no puede ser menor o igual a 0");
		
		this.length = length;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}
		
	public String getRandomString() {
		StringBuilder sb = new StringBuilder(this.length);
		
		for(int i=0; i<this.length; i++) {
			sb.append(alphabet.charAt(rn.nextInt(alphabet.length())));
		}
		
		return sb.toString();
	}
}
