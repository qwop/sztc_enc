package cn.szsctc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.szsctc.common.Const;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


//    @Test
    public void testEnc() throws UnsupportedEncodingException {
        App app = App.instance();
        // 原始数据
        byte[] data = "data".getBytes("utf-8");
        // 设备编号获取密钥进行加密
//        byte[] encData = app.enc( app.key( "CYX-184005"), data );
        byte[] encData = app.enc( app.key( "LRK001"), data );
    }

    @Test
    public void testEncStr() {
        String str = App.instance().encStr(null, null);
        assertEquals("", str);
    }

    @Test
    public void testEncDec() throws UnsupportedEncodingException {
        String content = "test中文";
        //随机生成密钥
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();

        String keyStr = new String(key, CharsetUtil.CHARSET_UTF_8);
        System.out.println( "密钥:" + keyStr);
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, keyStr.getBytes(CharsetUtil.CHARSET_UTF_8));
        //加密
        byte[] encrypt = aes.encrypt(content);
        //解密
        byte[] decrypt = aes.decrypt(encrypt);
        //加密为16进制表示
        String encryptHex = aes.encryptHex(content);
        //解密为字符串
        String decryptStr = aes.decryptStr(encryptHex, CharsetUtil.CHARSET_UTF_8);
        System.out.println( decryptStr );
    }
}
