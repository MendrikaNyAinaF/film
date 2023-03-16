package app.apps.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Filmset_unavailable extends Unavailable {

    @ManyToOne
    @JoinColumn(name = "filmset_id")
    private Filmset filmset_id;

}
