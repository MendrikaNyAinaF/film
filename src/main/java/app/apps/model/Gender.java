package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Gender extends HasName {

     public Gender() {

     }

     public Gender(Integer id) {
          setId(id);
     }
}
