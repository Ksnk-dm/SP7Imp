package ua.kiev.prog;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


public class Status {

    @JsonProperty("response_code")
    private String code;
    @JsonProperty("response_status")
    private String status;


    public Status() {
    }

    public Status(String code, String status) {
        this.code = code;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Status{" +
                "code='" + code + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
