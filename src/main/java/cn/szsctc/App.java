package cn.szsctc;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.szsctc.common.Const;
import cn.szsctc.model.KeyModel;
import cn.szsctc.thread.LogManager;
import cn.szsctc.thread.LogTaskFactory;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Aes加密方式
 * @author indiff
 */
public class App 
{
    // 本地测试地址
    // public static final String THIRD_API_URL = "https://localhost:9443/thirdApi";
    // 正式地址
    public static final String THIRD_API_URL = "https://jkjc.szsctc.cn/thirdApi";

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

    /**
     * 根据设备编号获取密钥
     * @param sn    设备编号
     * @return
     * @throws UnsupportedEncodingException
     */
    public KeyModel key( final String sn ) throws UnsupportedEncodingException {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("sn", sn );
        paramMap.put("dkl_time", System.currentTimeMillis() );
        String result= HttpUtil.post(THIRD_API_URL + "/sn/key", paramMap);

        if (StrUtil.isNotEmpty(result)) {
            JSONObject jsonObject = JSONUtil.parseObj(result);
            if (null != jsonObject) {
                int intRet = jsonObject.getInt("result");
                if (intRet == Const.RESULT_SUC) {
                    JSONObject dataSet = jsonObject.getJSONObject("dataSet");
                    int dklStatus = dataSet.getInt("dklStatus");
                    int dklType = dataSet.getInt("dklType");
                    String dklContent = dataSet.getStr("dklContent");

                    if (
                            dklStatus == Const.DKL_STAUTS_SUC &&
                            dklType == Const.DKL_TYPE_KEY &&
                            StrUtil.isNotEmpty(dklContent)
                    ) {
                        KeyModel keyModel = new KeyModel();
                        keyModel.setKey(dklContent.getBytes(Const.DEFAULT_CHARSET));
                        keyModel.setKeyStr(dklContent);
                        keyModel.setSn(sn);
                        return keyModel;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 根据密钥进行加密数据
     * @param key   密钥实体
     * @param content   原始数据
     * @return
     */
    public byte[] enc(KeyModel key, byte[] content) {
        //进行加密操作，构建AES
        LogManager.me().executeLog(LogTaskFactory.logEnc(key));
        if ( key == null ) {
            return null;
        }
        SymmetricCrypto aes = new SymmetricCrypto(SymmetricAlgorithm.AES, key.getKey());
        byte[] encrypt = aes.encrypt(content);
        return encrypt;
    }


    /**
     * 根据密钥进行加密数据
     * @param key   密钥实体
     * @param content   原始数据
     * @return utf-8格式数据
     */
    public String encStr(KeyModel key, byte[] content) {
        byte[] enc = enc(key, content);
        if ( null != enc ) {
            try {
                return new String(enc, Const.DEFAULT_CHARSET);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return "";
    }
}
