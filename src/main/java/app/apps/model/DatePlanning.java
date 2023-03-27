package app.apps.model;

import lombok.Getter;
import lombok.Setter;
import java.sql.Date;
import java.util.List;

@Getter
@Setter
public class DatePlanning {
    private Date jour_tournage;
    private List<Filmset> list_plateau;
    public DatePlanning(){}
}