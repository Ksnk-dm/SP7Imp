package ua.kiev.prog;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;


@Entity
@Table(name="Task")
public class Task {
    @Id
    @GeneratedValue
    @JsonIgnore
    private long id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;
    private String name;
    private String surname;
    private String description;
    private String date;
    private String phone;
    private String status;




    public Task() {
    }

    public Task(Group group, String name, String surname, String description, String date, String phone, String status) {
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.date = date;
        this.phone = phone;
        this.status = status;
    }

    public Task(Group group, String name, String surname, String description, String date, String phone) {
        this.group = group;
        this.name = name;
        this.surname = surname;
        this.description = description;
        this.date = date;
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", group=" + group +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", description='" + description + '\'' +
                ", date='" + date + '\'' +
                ", phone='" + phone + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}

