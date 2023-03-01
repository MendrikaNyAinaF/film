package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
public class Actor extends HasName {
    private Date birthdate;
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
