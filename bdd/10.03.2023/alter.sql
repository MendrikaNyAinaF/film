/*

    Ajout de la colonne 'preferred_shooting_time TIME' a la table scene

*/

ALTER TABLE scene ADD COLUMN preferred_shooting_time TIME;

INSERT into status_planning VALUES(0,'none');

CREATE OR REPLACE VIEW scene_status AS (
    SELECT
    Scene.*,
    0 AS status
    FROM scene
    WHERE Scene.id not in (select scene_id FROM planning)
    UNION    
    SELECT DISTINCT
    Scene.*,
    Planning.status AS status
    FROM Scene
    join Planning on
    Scene.id = Planning.scene_id
);
