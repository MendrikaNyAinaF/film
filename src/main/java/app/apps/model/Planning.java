package app.apps.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Planning extends HasId {
    private Integer scene_id;
    private Integer status;
    private Timestamp date;

    public Planning() {}

    public Planning(Integer id) {
        setId(id);
    }
}
