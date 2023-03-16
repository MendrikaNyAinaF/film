package app.apps.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class HasName extends HasId {

     @Column
     public String name;

     public HasName() {
     }
}
