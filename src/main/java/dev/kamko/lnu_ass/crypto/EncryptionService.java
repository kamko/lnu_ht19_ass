package dev.kamko.lnu_ass.crypto;

import org.jasypt.util.text.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptionService {

    private final TextEncryptor encryptor;

    public EncryptionService(TextEncryptor encryptor) {
        this.encryptor = encryptor;
    }

    public String encryptString(String val) {
        return encryptor.encrypt(val);
    }

    public String decryptString(String val) {
        return encryptor.decrypt(val);
    }
}
