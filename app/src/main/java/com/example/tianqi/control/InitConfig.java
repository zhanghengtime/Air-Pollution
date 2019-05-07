package com.example.tianqi.control;

import com.baidu.tts.client.SpeechSynthesizerListener;
import com.baidu.tts.client.TtsMode;

import java.util.Map;

/**
 * 合成引擎的初始化参数
 * <p>

 */

public class InitConfig {
    /**
     * 本demo的包名是com.example.tianqi，定义在build.gradle中。
     */
    private String appId;

    private String appKey;

    private String secretKey;

    /**
     * 纯在线或者离在线融合
     */
    private TtsMode ttsMode;


    /**
     * 初始化的其它参数，用于setParam
     */
    private Map<String, String> params;

    /**
     * 合成引擎的回调
     */
    private SpeechSynthesizerListener listener;

    private InitConfig() {

    }

    public InitConfig(String appId, String appKey, String secretKey, TtsMode ttsMode,
                      Map<String, String> params, SpeechSynthesizerListener listener) {
        this.appId = appId;
        this.appKey = appKey;
        this.secretKey = secretKey;
        this.ttsMode = ttsMode;
        this.params = params;
        this.listener = listener;
    }

    public SpeechSynthesizerListener getListener() {
        return listener;
    }

    public Map<String, String> getParams() {
        return params;
    }


    public String getAppId() {
        return appId;
    }

    public String getAppKey() {
        return appKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public TtsMode getTtsMode() {
        return ttsMode;
    }
}
