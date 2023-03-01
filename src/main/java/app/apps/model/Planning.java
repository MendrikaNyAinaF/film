package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

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
    private Timestamp date;

    public Planning() {
    }

    public Planning(Integer id) {
        setId(id);
    }
}
