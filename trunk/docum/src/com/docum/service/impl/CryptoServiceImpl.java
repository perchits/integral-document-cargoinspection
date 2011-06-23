package com.docum.service.impl;

import java.io.Serializable;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.docum.service.CryptoService;

@Service(CryptoService.SERVICE_NAME)
@Transactional
public class CryptoServiceImpl implements CryptoService, Serializable {

	private static final long serialVersionUID = -7737593399660021358L;
	private static final String DEFAULT_TEXT_PASSWORD = "an Arbytrary sTr1ng WHiCh is long en0ugh";
	private static final StandardPBEStringEncryptor TEXT_ENCRYPTOR = 
        createEncryptor(DEFAULT_TEXT_PASSWORD);
    
    @Override
    public String encryptText(String text) {
        return TEXT_ENCRYPTOR.encrypt(text);
    }

    @Override
    public String decryptText(String encryptedText) {
        return TEXT_ENCRYPTOR.decrypt(encryptedText);
    }
    
    private static StandardPBEStringEncryptor createEncryptor(String password) {
        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
        encryptor.setPassword(password);

        return encryptor;
    }

}
