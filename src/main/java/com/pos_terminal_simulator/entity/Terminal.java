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

    public Terminal(Builder builder){
        this.terminalId = builder.terminalId;
        this.merchantId = builder.merchantId;
        this.serialNumber = builder.serialNumber;
        this.terminalModel = builder.terminalModel;
        this.softwareVersion = builder.softwareVersion;
        this.currency = builder.currency;
        this.status = builder.status;
    };


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

    // Builder config
    public static class Builder {

        private String terminalId;
        private String merchantId;
        private String serialNumber;
        private String terminalModel;
        private String softwareVersion;
        private String currency;
        private String status;

        public Builder terminalId(String terminalId) {
            this.terminalId = terminalId;
            return this;
        }

        public Builder merchantId(String merchantId) {
            this.merchantId = merchantId;
            return this;
        }

        public Builder serialNumber(String serialNumber) {
            this.serialNumber = serialNumber;
            return this;
        }

        public Builder terminalModel(String terminalModel) {
            this.terminalModel = terminalModel;
            return this;
        }

        public Builder softwareVersion(String softwareVersion) {
            this.softwareVersion = softwareVersion;
            return this;
        }

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public Terminal build() {
            return new Terminal(this);
        }
    }
}