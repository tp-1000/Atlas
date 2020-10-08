package org.launchcode.Atlas.data;
import org.launchcode.Atlas.model.Marker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface MarkerRepository extends CrudRepository<Marker, Integer> {

    @Query(value = "SELECT * FROM marker WHERE ST_Distance(location, ST_GeographyFromText(?1)) < ?2", nativeQuery = true)
    List<Marker> getMarkerNearPoint(String point, double radius);

    List<Marker> findByUser_id(int user_id);
}
