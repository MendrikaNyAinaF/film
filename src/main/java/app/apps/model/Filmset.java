package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneTomany;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Filmset extends HasName {

     @OneToOne
     @JoinColumn(name="type_id")
    private TypeFilmset type;

     public Filmset() {

     }

     public Filmset(Integer id) {
          setId(id);
     }
}
