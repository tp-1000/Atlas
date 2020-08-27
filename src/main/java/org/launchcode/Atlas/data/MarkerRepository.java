package org.launchcode.Atlas.data;
import org.launchcode.Atlas.model.Marker;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarkerRepository extends CrudRepository<Marker, Integer> {

    //    @Query(value = "SELECT marker_name, ST_AsText(location) FROM marker WHERE ST_DWithin(ST_SetSRID(location, 4326), 'SRID=4326;POINT(38.5882554122321 -90.3487414811691)', 100)", nativeQuery = true)
    //    @Query(value = "SELECT * FROM marker WHERE ST_Distance(ST_SetSRID(location, 4326)\\:\\:geography, 'SRID=4326;POINT(38.5882554122321 -90.3487414811691)'\\:\\:geography ) < 400", nativeQuery = true)
    @Query(value = "SELECT * FROM marker WHERE ST_Distance(ST_SetSRID(location, 4326)\\:\\:geography, ?1\\:\\:geography ) < 400", nativeQuery = true)
    List<Marker> getMarkerNearPoint(String point);

}
