package com.docum.service;

public interface CryptoService {
	public static final String SERVICE_NAME = "cryptoService";
	
	public String encryptText(String text);
    public String decryptText(String encryptedText);

}
