package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Date;

@Entity
@Getter
@Setter
public class Film extends HasId {
    private String title;
    private String description;
    private Time duration;
    private Date start_shooting;
    private Integer nbr_team;
    private String visuel;

     public Film() {

     }

     public Film(Integer id) {
          setId(id);
     }
}
