package services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CoordinateRepository;
import domain.Chorbi;
import domain.Coordinate;
import domain.SearchTemplate;

@Transactional
@Service
public class CoordinateService {

	
	//Repository
	//======================================================================
	
	@Autowired
	private CoordinateRepository	coordinateRepository;
	
	//CRUD methods
	//=======================================================================
	
	
	/**
	 * 
	 * @param country
	 * @param province optional
	 * @param city
	 * @param state optional
	 * @param chorbi
	 * @param searchTemplate
	 * @return domain.coordinate
	 */
	
	public Coordinate create(String country, String city, Chorbi chorbi, SearchTemplate searchTemplate){
		
		Coordinate coordinate = new Coordinate();
		coordinate.setCity(city);
		coordinate.setCountry(country);
		coordinate.setChorbi(chorbi);
		coordinate.setSearchTemplate(searchTemplate);
		
		return coordinate;
	}
	
	public Coordinate save(Coordinate coordinate) {
		
		Assert.notNull(coordinate);
		
		return coordinateRepository.save(coordinate);
	}
	
	//Other bussiness methods
	//=======================================================================
	
	
	
	public Coordinate findByChorbiId(int chorbiId){
		Coordinate coordinate;
		Assert.notNull(chorbiId);
		
		coordinate = coordinateRepository.findByChorbiId(chorbiId);
	
		return coordinate;
		
	}

	

	
}
