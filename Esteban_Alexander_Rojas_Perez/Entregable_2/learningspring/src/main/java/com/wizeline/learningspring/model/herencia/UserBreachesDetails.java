package com.wizeline.learningspring.model.herencia;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UserBreachesDetails {
    @JsonProperty("PwnCount")
    private int pwnCount;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("LogoPath")
    private String logoPath;
    @JsonProperty("DataClasses")
    private List<String> dataClasses;
    @JsonProperty("IsVerified")
    private boolean isVerified;
    @JsonProperty("IsFabricated")
    private boolean isFabricated;
    @JsonProperty("IsSensitive")
    private boolean isSensitive;
    @JsonProperty("IsRetired")
    private boolean isRetired;
    @JsonProperty("IsSpamList")
    private boolean isSpamList;
    @JsonProperty("IsMalware")
    private boolean isMalware;

    public int getPwnCount() {
        return pwnCount;
    }

    public void setPwnCount(int pwnCount) {
        this.pwnCount = pwnCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLogoPath() {
        return logoPath;
    }

    public void setLogoPath(String logoPath) {
        this.logoPath = logoPath;
    }

    public List<String> getDataClasses() {
        return dataClasses;
    }

    public void setDataClasses(List<String> dataClasses) {
        this.dataClasses = dataClasses;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public boolean isFabricated() {
        return isFabricated;
    }

    public void setFabricated(boolean fabricated) {
        isFabricated = fabricated;
    }

    public boolean isSensitive() {
        return isSensitive;
    }

    public void setSensitive(boolean sensitive) {
        isSensitive = sensitive;
    }

    public boolean isRetired() {
        return isRetired;
    }

    public void setRetired(boolean retired) {
        isRetired = retired;
    }

    public boolean isSpamList() {
        return isSpamList;
    }

    public void setSpamList(boolean spamList) {
        isSpamList = spamList;
    }

    public boolean isMalware() {
        return isMalware;
    }

    public void setMalware(boolean malware) {
        isMalware = malware;
    }
}
