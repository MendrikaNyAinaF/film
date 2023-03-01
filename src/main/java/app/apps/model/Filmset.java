package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Filmset extends HasName {
    private TypeFilmset type;

     public Filmset() {

     }

     public Filmset(Integer id) {
          setId(id);
     }
}
