package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;

@Entity
@Getter
@Setter
public class Scene extends HasId {
    private String title;
    private String global_action;
    private Time time_start;
    private Time time_end;
    private Time estimated_time;

    @ManyToOne
    @JoinColumn(name = "filmset_id")
    private Filmset filmset;
    private Integer film_id;

    public Scene() {
    }

    public Scene(Integer id) {
        setId(id);
    }
}
