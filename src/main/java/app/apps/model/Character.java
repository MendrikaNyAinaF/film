package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Character extends HasName {
    private String description;
    private Integer gender;
    private Integer film_id;
    private Integer actor_id;

    public Character() {}

    public Character(Integer id) {
        setId(id);
    }
}
