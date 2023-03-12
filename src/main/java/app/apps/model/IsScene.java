package app.apps.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class IsScene extends HasId {

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

    public IsScene() {
    }

    public IsScene(Integer id) {
        setId(id);
    }
}
