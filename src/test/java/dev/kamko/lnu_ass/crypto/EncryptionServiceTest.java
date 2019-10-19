package dev.kamko.lnu_ass.crypto;

import org.jasypt.util.text.AES256TextEncryptor;
import org.jasypt.util.text.TextEncryptor;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class EncryptionServiceTest {

    private final EncryptionService sut = new EncryptionService(createEncryptor());

    private TextEncryptor createEncryptor() {
        var encryptor = new AES256TextEncryptor();
        encryptor.setPassword("123");

        return encryptor;
    }

    @Test
    void encryptionAndDecryptionWorks() {
        var original = "ABCD";

        var encrypted = sut.encryptString(original);
        assertThat(encrypted).isNotEqualTo(original);

        var decrypted = sut.decryptString(encrypted);
        assertThat(decrypted).isEqualTo(original);
    }
}