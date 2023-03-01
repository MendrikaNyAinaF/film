package app.apps.model;


import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dialogue extends HasId {

    @Column
    private Integer scene_id;
    @ManyToOne
    @JoinColumn(name = "character_id")
    private Character character;
    private String texte;
    private String action;

    public Dialogue() {
    }

    public Dialogue(Integer id) {
        setId(id);
    }
}
