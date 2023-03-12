package app.apps.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Date;

@Entity(name = "film")
@Getter
@Setter
public class Film extends HasId {

     @Column
     private String title;

     @Column
     private String description;

     @Column
     private Time duration;

     @Column
     private Integer nbr_team;

     @Column
     private String visuel;

     public Film() {

     }

     public Film(Integer id) {
          setId(id);
     }
}
