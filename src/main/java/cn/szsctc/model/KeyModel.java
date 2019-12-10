package cn.szsctc.model;

import lombok.Builder;
import lombok.Data;
import lombok.Value;
import lombok.experimental.Accessors;

@Data
@Accessors(chain=true)
public class KeyModel {

    public KeyModel() {
        timestamp = "" + System.currentTimeMillis();
    }
    private String sn;
    private String timestamp;
    private byte[] key;
    private String keyStr;
}
