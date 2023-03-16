package app.apps.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Holiday extends HasName{
    @Column
    private Date date;
}
