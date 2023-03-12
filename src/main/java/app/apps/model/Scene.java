package app.apps.model;

import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Scene extends IsScene {
    public Scene(){}
    public Scene(Integer id){
        super(id);
    }
}
