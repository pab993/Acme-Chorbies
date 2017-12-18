package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.LikeRepository;
import domain.Chorbi;
import domain.Like;

@Transactional
@Service
public class LikeService {

	
	//Repository
	//======================================================================
	
	@Autowired
	private LikeRepository	likeRepository;
	
	@Autowired
	private ChorbiService chorbiService;
	
	//Services
	//======================================================================
	
	
	// Constructor methods
	// =====================================================================
	
	public LikeService() {
		super();
	}
	
	//CRUD methods
	//=======================================================================
	
	public Like findOne(int id){
		Assert.isTrue(likeRepository.exists(id));
		
		Like like = likeRepository.findOne(id);
		Assert.notNull(like);
		
		return like;
	}
	
	public Collection<Like> findAll() {
		Collection<Like> result;

		result = likeRepository.findAll();

		return result;
	}
	
	//Other bussiness methods
	//=======================================================================
	
	
	public Collection<Integer> minStarsByChorbi(){
		
		return likeRepository.minStarsByChorbi();
	}
	
	
	public Double avgLikesPerChorbi(){
		Double result;
		
		result = likeRepository.avgLikesPerChorbi();
		Assert.notNull(result);
		
		return result;
	}
	
	
	public int maxLikesPerChorbi(){
		int result;
		
		result = likeRepository.maxLikesPerChorbi();
		Assert.notNull(result);
		
		return result;
	}
	
	
	public int minLikesPerChorbi(){
		int result;
		
		result = likeRepository.minLikesPerChorbi();
		Assert.notNull(result);
		
		return result;
	}

	public Like create(Chorbi chorbi) {
		Like like = new Like();
		
		Chorbi principal = chorbiService.findByPrincipal();
		
		Assert.isTrue(principal.getId() != chorbi.getId()); // un chorbi no puede gustarse a sí mismo
		
		like.setCreateMoment(new Date(System.currentTimeMillis()-100));
		
		like.setChorbiSender(principal);
		like.setChorbiRecipient(chorbi);
		like.setStars(0);
		
		return like;
	}

	public Like save(Like like) {
		// TODO Auto-generated method stub
		Assert.notNull(like);
		
		Like saved = likeRepository.save(like);
		
		
		return saved;
	}

	@Autowired
	private Validator validator;
	
	public Like reconstruct(Like like, BindingResult binding) {
		// TODO Auto-generated method stub
		
		Like resul;
		
		if(like.getId() == 0)
			resul = like;
		else{
			
			resul = likeRepository.findOne(like.getId());
			
			resul.setComment(like.getComment());
			resul.setStars(like.getStars());
		}
		
		validator.validate(resul, binding);
		
		return resul;
	}

	public void delete(Like like) {
		// TODO Auto-generated method stub
		
		Assert.notNull(like);
		Assert.isTrue(likeRepository.exists(like.getId()));
		likeRepository.delete(like.getId());
	}

	public Like findOneBySenderAndRecipient(int senderId, int recipientId) {
		// TODO Auto-generated method stub
		
		Assert.notNull(senderId);
		Assert.notNull(recipientId);
		
		Like like = likeRepository.findOneBySenderAndRecipient(senderId, recipientId);
		
		Assert.notNull(like);
		
		return like;
	}

	public Collection<Integer> maxStarsByChorbi() {
		// TODO Auto-generated method stub
		return likeRepository.maxStarsByChorbi();
		
	}
	
	public Collection<Double> avgStarsByChorbi() {
		// TODO Auto-generated method stub
		return likeRepository.avgStarsByChorbi();
	}

	public Collection<String> findAllChorbiesSortedByAVGStars() {
		// TODO Auto-generated method stub
		return likeRepository.findAllChorbiesSortedByAVGStars();
	}
}
