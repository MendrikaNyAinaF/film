package app.apps.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Actor extends HasName {

    @Column
    private Date birthdate;

    @Column
    private String contact;

    @ManyToOne
    @JoinColumn(name = "gender")
    private Gender gender;

    public Actor() {
    }

    public Actor(Integer id) {
        setId(id);
    }
}