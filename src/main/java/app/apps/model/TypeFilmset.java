package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "type_filmset")
@Getter
@Setter
public class TypeFilmset extends HasName {

     public TypeFilmset() {

     }

     public TypeFilmset(Integer id) {
          setId(id);
     }
}
