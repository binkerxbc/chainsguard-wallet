package com.chainsguard.wallet.data.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author i11m20n
 */
public final class TransferResult {

    @Expose
    @SerializedName("txhash")
    private String txhash;
    @Expose
    @SerializedName("raw_log")
    private String rawLog;
    @Expose
    @SerializedName("logs")
    private List<LogsBean> logs;
    @Expose
    @SerializedName("info")
    private String info;
    @Expose
    @SerializedName("height")
    private int height;
    @Expose
    @SerializedName("events")
    private List<EventsBean> events;
    @Expose
    @SerializedName("data")
    private String data;
    @Expose
    @SerializedName("codespace")
    private String codespace;
    @Expose
    @SerializedName("code")
    private int code;

    public String getTxhash() {
        return txhash;
    }

    public void setTxhash(String txhash) {
        this.txhash = txhash;
    }

    public String getRawLog() {
        return rawLog;
    }

    public void setRawLog(String rawLog) {
        this.rawLog = rawLog;
    }

    public List<LogsBean> getLogs() {
        return logs;
    }

    public void setLogs(List<LogsBean> logs) {
        this.logs = logs;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public List<EventsBean> getEvents() {
        return events;
    }

    public void setEvents(List<EventsBean> events) {
        this.events = events;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCodespace() {
        return codespace;
    }

    public void setCodespace(String codespace) {
        this.codespace = codespace;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public static class LogsBean {
        @Expose
        @SerializedName("success")
        private boolean success;
        @Expose
        @SerializedName("msg_index")
        private int msgIndex;
        @Expose
        @SerializedName("log")
        private String log;

        public boolean getSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public int getMsgIndex() {
            return msgIndex;
        }

        public void setMsgIndex(int msgIndex) {
            this.msgIndex = msgIndex;
        }

        public String getLog() {
            return log;
        }

        public void setLog(String log) {
            this.log = log;
        }
    }

    public static class EventsBean {
        @Expose
        @SerializedName("type")
        private String type;
        @Expose
        @SerializedName("attributes")
        private List<AttributesBean> attributes;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<AttributesBean> getAttributes() {
            return attributes;
        }

        public void setAttributes(List<AttributesBean> attributes) {
            this.attributes = attributes;
        }
    }

    public static class AttributesBean {
        @Expose
        @SerializedName("value")
        private String value;
        @Expose
        @SerializedName("key")
        private String key;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    @Override
    public String toString() {
        return "TransferResult{" +
                "txhash='" + txhash + '\'' +
                ", rawLog='" + rawLog + '\'' +
                ", logs=" + logs +
                ", info='" + info + '\'' +
                ", height=" + height +
                ", events=" + events +
                ", data='" + data + '\'' +
                ", codespace='" + codespace + '\'' +
                ", code=" + code +
                '}';
    }
}
