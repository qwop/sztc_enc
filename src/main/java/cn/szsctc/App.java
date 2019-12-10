package cn.szsctc;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.szsctc.common.Const;
import cn.szsctc.model.KeyModel;

import java.io.UnsupportedEncodingException;

/**
 * Aes加密方式
 *
 */
public class App 
{
    private static App app= null;
    private App() {

    }



    public static App instance() {
        if ( null == app ) {
            synchronized(App.class) {
                if ( null == app ) {
                    app = new App();
                }
            }
        }
        return app;
    }

    public KeyModel key(final String sn ) throws UnsupportedEncodingException {
        byte[] key = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        KeyModel keyModel = new KeyModel();
        keyModel.setKey(key);
        keyModel.setKeyStr(new String(key, Const.DEFAULT_CHARSET));
        keyModel.setSn(sn);
        return keyModel;
    }

    public byte[] enc(KeyModel key, byte[] content) {
        //构建
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getKey());
        byte[] encrypt = aes.encrypt(content);
        return encrypt;
    }
}
