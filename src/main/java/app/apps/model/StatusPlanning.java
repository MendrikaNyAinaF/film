package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "status_planning")
@Getter
@Setter
public class StatusPlanning extends HasName {

    public StatusPlanning() {
        super();
    }

    public StatusPlanning(Integer id) {
        setId(id);
    }
}
