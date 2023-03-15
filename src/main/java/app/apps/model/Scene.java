package app.apps.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Scene extends HasId {

    @Column
    private String title;

    @Column
    private String global_action;

    @Column
    private Time time_start;

    @Column
    private Time time_end;

    @Column
    private Time estimated_time;

    @ManyToOne
    @JoinColumn(name = "filmset_id")
    private Filmset filmset;

    @Column
    private Integer film_id;

    @Column
    private Time preferred_shooting_time;

    @ManyToOne
    @JoinColumn(name = "status")
    private StatusPlanning status;

    @Column
    private Integer ordre;

    public Scene() {
    }

    public Scene(Integer id) {
        setId(id);
    }
}
