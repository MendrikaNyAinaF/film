package app.apps.model;

import java.sql.Time;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Immutable;

@Entity
@Immutable
@Getter
@Setter
public class Scene_status extends IsScene {

    @ManyToOne
    @JoinColumn(name = "status")
    private StatusPlanning status;

    public Scene_status() {
    }

    public Scene_status(Integer id) {
        setId(id);
    }
}
