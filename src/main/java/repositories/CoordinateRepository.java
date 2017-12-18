package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Coordinate;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Integer>{

	@Query("select c from Coordinate c where c.chorbi.id = ?1")
	Coordinate findByChorbiId(int id);
}
