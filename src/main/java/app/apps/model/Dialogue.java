package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneTomany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Dialogue extends HasId {
    private Integer scene_id;
    @ManyToOne
    @JoinColumn(name="character_id")
    private Character character;
    private String texte;
    private String action;

    public Dialogue() {}

    public Dialogue(Integer id) {
        setId(id);
    }
}
