package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Actor extends HasName {
    private Date birthdate;
    private String contact;
    private Gender gender;

    public Actor() {}

    public Actor(Integer id) {
        setId(id);
    }
}
