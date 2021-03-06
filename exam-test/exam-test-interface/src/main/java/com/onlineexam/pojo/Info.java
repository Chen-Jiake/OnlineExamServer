package com.onlineexam.pojo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 后台日志
 */
@Table(name = "info")
public class Info {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long infoId;

    private String infoUsername;

    private Date infoVisittime;

    private String infoIp;

    private String infoUri;

    private String infoDescribe;

    public Info() {
    }

    public Long getInfoId() {
        return infoId;
    }

    public void setInfoId(Long infoId) {
        this.infoId = infoId;
    }

    public String getInfoUsername() {
        return infoUsername;
    }

    public void setInfoUsername(String infoUsername) {
        this.infoUsername = infoUsername;
    }

    public Date getInfoVisittime() {
        return infoVisittime;
    }

    public void setInfoVisittime(Date infoVisittime) {
        this.infoVisittime = infoVisittime;
    }

    public String getInfoIp() {
        return infoIp;
    }

    public void setInfoIp(String infoIp) {
        this.infoIp = infoIp;
    }

    public String getInfoUri() {
        return infoUri;
    }

    public void setInfoUri(String infoUri) {
        this.infoUri = infoUri;
    }

    public String getInfoDescribe() {
        return infoDescribe;
    }

    public void setInfoDescribe(String infoDescribe) {
        this.infoDescribe = infoDescribe;
    }

    @Override
    public String toString() {
        return "Info{" +
                "infoId=" + infoId +
                ", infoUsername='" + infoUsername + '\'' +
                ", infoVisittime=" + infoVisittime +
                ", infoIp='" + infoIp + '\'' +
                ", infoUri='" + infoUri + '\'' +
                ", infoDescribe='" + infoDescribe + '\'' +
                '}';
    }
}
