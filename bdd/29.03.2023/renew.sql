delete from planning;
delete from filmset_unavailable where id>1;
delete from actor_unavailable where id>1;
UPDATE scene SET status=3 WHERE id IN (7,1,5,11,2,3,10,16);
