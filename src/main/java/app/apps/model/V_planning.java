package app.apps.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Timestamp;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class V_planning extends HasId{

    @Column
    private Integer scene_id;

    @Column
    private Integer status;

    @Column
    private Timestamp date_debut;

    @Column
    private Timestamp date_fin;

    @Column
    private String title;
}
