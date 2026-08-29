package com.pos_terminal_simulator.entity;

public class Terminal {

    private Long id;
    private String terminalId;
    private String merchantId;
    private String serialNumber;
    private String terminalModel;
    private String softwareVersion;
    private String currency;
    private String status;

    public Terminal(){};

    public Terminal(
            String terminalId,
            String merchantId,
            String serialNumber,
            String terminalModel,
            String softwareVersion,
            String currency,
            String status
    ) {
        this.terminalId = terminalId;
        this.merchantId = merchantId;
        this.serialNumber = serialNumber;
        this.terminalModel = terminalModel;
        this.softwareVersion = softwareVersion;
        this.currency = currency;
        this.status = status;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}