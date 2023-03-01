package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dialogue extends HasId {
    private Integer scene_id;
    private Integer character_id;
    private String texte;
    private String action;

    public Dialogue() {}

    public Dialogue(Integer id) {
        setId(id);
    }
}
