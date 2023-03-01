SELECT * FROM planning
WHERE scene_id IN (SELECT id FROM scene WHERE film_id = nbr);
