package app.apps.model;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Author extends HasName {
     public Author() {

     }

     public Author(Integer id) {
          setId(id);
     }
}
