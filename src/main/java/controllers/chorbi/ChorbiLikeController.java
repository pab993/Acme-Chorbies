package controllers.chorbi;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChorbiService;
import services.LikeService;
import domain.Chorbi;
import domain.Like;

@Transactional
@Service
@RequestMapping("/like/chorbi")
public class ChorbiLikeController {
	
	//principal service
	
	@Autowired
	LikeService likeService;
	
	//Contructor
	
	
	public ChorbiLikeController(){
		super();
	}
	
	//Suporting services
	
	@Autowired
	private ChorbiService chorbiService;
	
	// Create
	// ==========================================================
	
	@RequestMapping(value="create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam int chorbiId){
		
		ModelAndView resul;
		
		Chorbi chorbi = chorbiService.findOne(chorbiId);
		
		Like like = likeService.create(chorbi);
		try{
			Like saved = likeService.save(like);
			resul = createEditModelAndView(saved);
		}catch (Throwable oops) {
			
			resul = new ModelAndView("redirect:/chorbi/list.do");
		}
		
		
		
		return resul;
	}
	
	// Edit
	// ==========================================================
	
	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(Like like, BindingResult binding){
		ModelAndView resul;
		
		
		
		like = likeService.reconstruct(like, binding);
		
		if(binding.hasErrors())
			resul = createEditModelAndView(like);
		else{
			
			try{
				
				likeService.save(like);
				resul = new ModelAndView("redirect:/chorbi/list.do");
				
			}catch (Throwable oops) {
				resul = createEditModelAndView("like.error.commit", like);
			}
			
		}
		return resul;
	}

	
	

	

	// Delete
	// ==========================================================
	
	@RequestMapping(value = "delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam int chorbiId){
		
		ModelAndView result;
		
		Chorbi sender = chorbiService.findByPrincipal();
		
		Like like = likeService.findOneBySenderAndRecipient(sender.getId(), chorbiId);
		
		try {
			
			likeService.delete(like);
			
			result = new ModelAndView("redirect:/chorbi/list.do");
			
		} catch (Throwable oops) {
			result = createListModelAndView("like.error.delete");
		}
		
		return result;
	}
	
	private ModelAndView createListModelAndView(String message) {
		// TODO Auto-generated method stub
		
		ModelAndView result;
		Collection<Chorbi> chorbies;
		Chorbi principal = chorbiService.findByPrincipal();

		chorbies = chorbiService.findAllChorbies();
		
		chorbies.remove(principal);

		result = new ModelAndView("chorbi/list");
		result.addObject("chorbies", chorbies);
		result.addObject("requestURI", "chorbi/list.do");
		
		
		// Chorbis likes
		
		
		Collection<Like> likesSendedByPrincipal = principal.getLikesSended();
		
		Collection<Chorbi> chorbiLikeReceivedFromPrincipal = new ArrayList<Chorbi>();
		
		for (Like like : likesSendedByPrincipal) {
			chorbiLikeReceivedFromPrincipal.add(like.getChorbiRecipient());
		}
		
		
		result.addObject("chorbiLikeReceivedFromPrincipal", chorbiLikeReceivedFromPrincipal);
		
		return result;
	}

	//Ancilliary methods
	
	
	private ModelAndView createEditModelAndView(Like like) {	
		return createEditModelAndView(null, like);
	}

	private ModelAndView createEditModelAndView(String message, Like like) {
		
		ModelAndView resul = new ModelAndView("like/edit");
		
		resul.addObject("like", like);
		
		resul.addObject("message", message);
		return resul;
	}
}
