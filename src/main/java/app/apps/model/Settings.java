package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Settings extends HasName {
     private Double value;

     public Settings() {

     }

     public Settings(Integer id) {
          setId(id);
     }
}
