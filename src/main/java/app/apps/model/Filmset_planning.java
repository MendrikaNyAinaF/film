package app.apps.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Time;

@Entity
@Getter
@Setter
public class Filmset_planning extends HasId {

    @Column
    Integer weekday;

    @Column
    Time timestart;

    @Column
    Time timeend;

    @ManyToOne
    @JoinColumn(name = "filmset_id")
    private Filmset filmset;

}
