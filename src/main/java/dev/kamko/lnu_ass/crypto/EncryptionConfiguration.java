package dev.kamko.lnu_ass.crypto;

import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EncryptionConfiguration {

    @Bean
    TextEncryptor encryptor(@Value("${encryption.key") String encryptionKey) {
        var encryptor = new AES256TextEncryptor();
        encryptor.setPassword(encryptionKey);

        return encryptor;
    }
}
