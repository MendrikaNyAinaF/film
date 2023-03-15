package app.apps.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Getter
@Setter
@MappedSuperclass
public class Filmset_unavailable extends HasId {

    @Column
    Date date_debut;

    @Column
    Date date_fin;

    @Column
    String observation;

}
