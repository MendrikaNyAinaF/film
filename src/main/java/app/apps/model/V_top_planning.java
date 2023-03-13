package app.apps.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class V_top_planning extends HasName{
    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeFilmset type;

    @Column(name = "x")
    private Double x;

    @Column(name = "y")
    private Double y;

    @Column(name = "uses")
    private Integer uses;

}
