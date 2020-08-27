package org.launchcode.Atlas.data;

import org.launchcode.Atlas.model.Marker;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkerRepository extends CrudRepository<Marker, Integer> {

}
