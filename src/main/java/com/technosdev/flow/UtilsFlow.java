package com.technosdev.flow;

import com.technosdev.flow.entities.DecryptionInfo;
import com.technosdev.resources.FlowHandlerResource;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.OAEPParameterSpec;
import javax.crypto.spec.PSource;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.MGF1ParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Logger;

public class UtilsFlow {
    private static final Logger LOGGER = Logger.getLogger(FlowHandlerResource.class.getName());
    private static final int AES_KEY_SIZE = 128;
    private static final String KEY_GENERATOR_ALGORITHM = "AES";
    private static final String AES_CIPHER_ALGORITHM = "AES/GCM/NoPadding";
    private static final String RSA_ENCRYPT_ALGORITHM = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";
    private static final String RSA_MD_NAME = "SHA-256";
    private static final String RSA_MGF = "MGF1";

    static {
        Security.addProvider(new BouncyCastleProvider());
    }

    public static DecryptionInfo decryptRequestPayload(byte[] encrypted_flow_data, byte[] encrypted_aes_key, byte[] initial_vector) throws Exception {
        final RSAPrivateKey privateKey = readPrivateKeyFromPkcs8UnencryptedPem("src/main/java/com/technosdev/flow/private_unencrypted_pkcs8.pem");
        LOGGER.info("Private key read successfully");
        final byte[] aes_key = decryptUsingRSA(privateKey, encrypted_aes_key);
        LOGGER.info("AES key decrypted successfully");
        return new DecryptionInfo(decryptUsingAES(encrypted_flow_data, aes_key, initial_vector), aes_key);
    }

    private static RSAPrivateKey readPrivateKeyFromPkcs8UnencryptedPem(String filePath) throws Exception {
        String key = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);
        key = key.replaceAll("-----BEGIN PRIVATE KEY-----", "")
                .replaceAll("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        byte[] encoded = Base64.getDecoder().decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
        return (RSAPrivateKey) keyFactory.generatePrivate(keySpec);
    }

    private static byte[] decryptUsingRSA(final RSAPrivateKey privateKey, final byte[] payload) throws GeneralSecurityException {
        final Cipher cipher = Cipher.getInstance(RSA_ENCRYPT_ALGORITHM, "BC");
        cipher.init(Cipher.DECRYPT_MODE, privateKey, new OAEPParameterSpec(RSA_MD_NAME, RSA_MGF, MGF1ParameterSpec.SHA256, PSource.PSpecified.DEFAULT));
        return cipher.doFinal(payload);
    }

    private static String decryptUsingAES(final byte[] encrypted_payload, final byte[] aes_key, final byte[] iv) throws GeneralSecurityException {
        final GCMParameterSpec paramSpec = new GCMParameterSpec(AES_KEY_SIZE, iv);
        final Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(aes_key, KEY_GENERATOR_ALGORITHM), paramSpec);
        final byte[] data = cipher.doFinal(encrypted_payload);
        return new String(data, StandardCharsets.UTF_8);
    }

    public static String encryptAndEncodeResponse(final String clearResponse, final byte[] aes_key, final byte[] iv) throws GeneralSecurityException {
        final GCMParameterSpec paramSpec = new GCMParameterSpec(AES_KEY_SIZE, iv);
        final Cipher cipher = Cipher.getInstance(AES_CIPHER_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(aes_key, KEY_GENERATOR_ALGORITHM), paramSpec);
        final byte[] encryptedData = cipher.doFinal(clearResponse.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(encryptedData);
    }

    public static byte[] flipIv(final byte[] iv) {
        final byte[] result = new byte[iv.length];
        for (int i = 0; i < iv.length; i++) {
            result[i] = (byte) (iv[i] ^ 0xFF);
        }
        return result;
    }
}
