package app.apps.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class HasId {
     @Id
     @GeneratedValue(strategy = IDENTITY)
     public Integer id;

     public HasId() {
     }
}
