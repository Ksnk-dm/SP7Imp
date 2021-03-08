package ua.kiev.prog;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Status {
    @Id
    @GeneratedValue
    private long id;
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
                "id=" + id +
                ", code='" + code + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
