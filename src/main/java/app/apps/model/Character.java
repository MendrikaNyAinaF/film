package app.apps.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Character extends HasName {

    @Column
    private String description;

    @ManyToOne
    @JoinColumn(name = "gender")
    private Gender gender;
    private Integer film_id;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;

    public Character() {
    }

    public Character(Integer id) {
        setId(id);
    }
}
