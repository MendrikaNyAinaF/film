package app.apps.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Planning extends HasId {
    @ManyToOne
    @JoinColumn(name = "scene_id")
    private Scene scene;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private StatusPlanning status;

    @Column
    private Timestamp date_debut;

    @Column
    private Timestamp date_fin;

    public Planning() {
    }

    public Planning(Integer id) {
        setId(id);
    }
}
