package org.atlas.flight.core.util.encrypt;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES 암복호화 util
 * */
@Slf4j
public final class CryptoUtil {

    /* 인스턴스화를 방지하기 위한 생성자 접근제어 */
    private CryptoUtil(){
        throw new UnsupportedOperationException();
    }

    /**
     * 주어진 키를 바탕으로 SecretKeySpec 객체를 생성합니다.
     * 키는 UTF-8 문자열로부터 16바이트로 제한되며, 이를 AES 암호화 키로 사용합니다.
     *
     * @param key 암호화 키로 사용될 문자열
     * @return AES 암호화에 사용될 Key 객체
     */
    private static Key generateKeySpec(String key) {
        byte[] keyBytes = new byte[16];
        byte[] b = key.getBytes(StandardCharsets.UTF_8);
        int len = b.length;
        if (len > keyBytes.length) {
            len = keyBytes.length;
        }
        System.arraycopy(b, 0, keyBytes, 0, len);
        return new SecretKeySpec(keyBytes, "AES");
    }

    /**
     * 주어진 문자열을 AES/CBC/PKCS5Padding 알고리즘을 이용하여 암호화합니다.
     * 초기 벡터(IV)는 키의 첫 16바이트를 사용하며, 결과는 Base64로 인코딩되어 반환됩니다.
     *
     * @param key 암호화에 사용될 키
     * @param str 암호화할 문자열
     * @return Base64 인코딩된 암호화 문자열
     */
    public static String AES_Encrypt(String key, String str) {

        String enStr = null;

        try {
            String iv = key.substring(0,16);
            Key keySpec = generateKeySpec(key);
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
            byte[] encrypted = c.doFinal(str.getBytes(StandardCharsets.UTF_8));
            enStr = new String(Base64.encodeBase64(encrypted));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return enStr;
    }

    /**
     * AES/CBC/PKCS5Padding 알고리즘을 이용하여 암호화된 문자열을 복호화합니다.
     * 초기 벡터(IV)는 키의 첫 16바이트를 사용하며, 입력 문자열은 Base64로 디코딩되어야 합니다.
     *
     * @param key 복호화에 사용될 키
     * @param str 복호화할 Base64 인코딩된 문자열
     * @return 복호화된 원본 문자열
     */
    public static String AES_Decrypt(String key, String str) {

        String ret = null;

        try {
            if (StringUtils.isEmpty(str)) {
                return "";
            }
            Key keySpec = generateKeySpec(key);
            String iv = key.substring(0,16);
            Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes(StandardCharsets.UTF_8)));
            byte[] byteStr = Base64.decodeBase64(str.getBytes());
            ret = new String(c.doFinal(byteStr), StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return ret;
    }

    //단방향 salt 생성*
    //DRGB로 만들어진 16바이트 salt가 생성됨.
    //return - string
    public static String makeHashSalt() throws UnsupportedEncodingException {

        String saltString = null;
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[8];
        StringBuffer salt = new StringBuffer();

        random.nextBytes(saltBytes);

        for (int i = 0; i < saltBytes.length; i++) {
            salt.append(String.format("%02x", saltBytes[i]));
        }
        saltString = new String(salt.toString().getBytes(), StandardCharsets.UTF_8);

        return saltString;
    }

    //	bytes에는 해쉬할 스트링 (pw + salt)의bytes 값이 들어 간다. cryptoType을 SHA2의 bit 수가 들어 간다. EX)SHA-256
    //	1회성 hash를 할때 사용
    public static String makeHash(byte[] bytes, String cryptoType) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String ret = null;

            // Create crypto_type Hash
            MessageDigest digest = MessageDigest.getInstance(cryptoType);
            digest.update(bytes);
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String h = Integer.toHexString(0xFF & messageDigest[i]);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }

            ret = new String(hexString.toString().getBytes(), StandardCharsets.UTF_8);


        return ret;

    }

}
