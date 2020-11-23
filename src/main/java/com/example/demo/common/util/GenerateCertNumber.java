package com.example.demo.common.util;

import java.util.Random;

public class GenerateCertNumber {
	/*
	public static void main(String[] args) {
        GenerateCertNumber ge = new GenerateCertNumber();
        ge.setPwdLength(3);
        String pwd = ge.excuteGenerate();
        String salt = SHA256Util.generateSalt();
		String encPassword = SHA256Util.getEncrypt(pwd, salt);
       
    }
    */
	private int passwordLength;
	private final char[] passwordTable =  { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X',
            'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 
            'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&', '*',
            '(', ')', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0' };



    
    public String excuteGenerate(int passwordLength) {
    	this.passwordLength = passwordLength;
        Random random = new Random(System.currentTimeMillis());
        int tablelength = passwordTable.length;
        StringBuffer buf = new StringBuffer();
        
        for(int i = 0; i < passwordLength; i++) {
            buf.append(passwordTable[random.nextInt(tablelength)]);
        }
        
        return buf.toString();
    }




	public int getPasswordLength() {
		return passwordLength;
	}




	public void setPasswordLength(int passwordLength) {
		this.passwordLength = passwordLength;
	}



}
