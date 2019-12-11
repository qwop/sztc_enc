package cn.szsctc.thread;

import cn.hutool.http.HttpUtil;
import cn.szsctc.model.KeyModel;

import java.util.HashMap;
import java.util.TimerTask;

import static cn.szsctc.App.THIRD_API_URL;

/**
 * @author indiff
 */
public class LogTaskFactory {
    public static TimerTask logEnc(final KeyModel keyModel) {
        return new TimerTask() {
            @Override
            public void run() {
                HashMap<String, Object> paramMap = new HashMap<>();
                String sn = keyModel == null ? "" : keyModel.getSn();
                String keyStr =  keyModel == null ? "" : keyModel.getKeyStr();
                paramMap.put("sn", sn);
                paramMap.put("dkl_key", keyStr);
                paramMap.put("dkl_time", System.currentTimeMillis() );
                HttpUtil.post(THIRD_API_URL + "/sn/enc", paramMap);
            }
        };
    }
}