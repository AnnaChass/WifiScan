package com.example.mobile1;

public class Element {
    private String SSID;
    private String BSSID;
    private String level;
    private String frequency;
    private String encrypt;
    private String extra;


    public Element(String _SSID, String _BSSID, String _level, String _frequency, String _encrypt, String _extra)
    {
        this.SSID = _SSID;
        this.BSSID = _BSSID;
        this.level = _level;
        this.frequency = _frequency;
        this.encrypt = _encrypt;
        this.extra = _extra;
    }

    public String getSSID()
    {
        return SSID;
    }

    public String getBSSID()
    {
        return BSSID;
    }

    public String getLevel()
    {
        return level;
    }

    public String getFrequency()
    {
        return frequency;
    }

    public String getEncrypt()
    {
        return encrypt;
    }

    public String getExtra()
    {
        return extra;
    }
}
