package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Column;
import javax.persistence.Transient;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Filmset extends HasName {

     @ManyToOne
     @JoinColumn(name = "type_id")
     private TypeFilmset type;

     @Column(name = "x")
     private Double x;

     @Column(name = "y")
     private Double y;

     @Transient
     private List<Planning> list_planning;

     public Filmset() {

     }

     public Filmset(Integer id) {
          setId(id);
     }
}
