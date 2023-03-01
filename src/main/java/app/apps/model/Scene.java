package app.apps.model;

@Entity
@Getter
@Setter
public class Scene extends HasId {

    @Column
    private String title;

    @Column
    private String global_action;

    @Column
    private Time time_start;

    @Column
    private Time time_end;

    @Column
    private Time estimated_time;

    @ManyToOne
    @JoinColumn(name = "filmset_id")
    private Filmset filmset;
    
    @Column
    private Integer film_id;

    public Scene() {
    }

    public Scene(Integer id) {
        setId(id);
    }
}

