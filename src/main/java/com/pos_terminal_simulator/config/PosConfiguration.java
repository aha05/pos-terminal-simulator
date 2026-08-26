package com.pos_terminal_simulator.config;

public class PosConfiguration {

    private String terminalId;
    private String merchantId;
    private String serialNumber;
    private String terminalModel;
    private String softwareVersion;

    public PosConfiguration() {
        this.terminalId = "TERM0001";
        this.merchantId = "MERCHANT000001";
        this.serialNumber = "POS-SERIAL-001";
        this.terminalModel = "POS-SIMULATOR";
        this.softwareVersion = "1.0.0";
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getTerminalModel() {
        return terminalModel;
    }

    public void setTerminalModel(String terminalModel) {
        this.terminalModel = terminalModel;
    }

    public String getSoftwareVersion() {
        return softwareVersion;
    }

    public void setSoftwareVersion(String softwareVersion) {
        this.softwareVersion = softwareVersion;
    }
}
