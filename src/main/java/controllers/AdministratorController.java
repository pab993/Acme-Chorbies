/*
 * AdministratorController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.ChorbiService;
import services.LikeService;
import services.ManagerService;
import domain.Chorbi;
import domain.Manager;


@Controller
@RequestMapping("/administrator")
public class AdministratorController extends AbstractController {

	// Constructors -----------------------------------------------------------

	public AdministratorController() {
		super();
	}

	// Services
	// ========================================================================
	@Autowired
	private ChorbiService chorbiService;
	
	@Autowired
	private ChirpService chirpService;

	@Autowired
	private LikeService likeService;
	
	@Autowired
	private ManagerService managerService;

	
	@RequestMapping(value = "/ban", method = RequestMethod.GET)
	public ModelAndView ban(@RequestParam int chorbiId) {
		ModelAndView resul;
		try {
			chorbiService.ban(chorbiId);
			resul = new ModelAndView("redirect:/chorbi/adminList.do");
		} catch (Throwable exception) {
			resul = new ModelAndView("redirect:/chorbi/adminList.do");
		}

		return resul;
	}
	@RequestMapping(value = "/updateFees", method = RequestMethod.GET)
	public ModelAndView updateFees() {
		ModelAndView resul;
		boolean error= false;
		try {
			chorbiService.updateFees();
			resul = new ModelAndView();
			resul.addObject("error", error);
		} catch (Throwable exception) {
			error = true;
		
			resul = new ModelAndView();
			resul.addObject("error", error);
		}
		resul.addObject("requestURI", "administrator/updateFees.do");
		return resul;
	}

	// DashBoard
		// ============================================================================

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		
		Collection<Chorbi> listChorbiesByCountry = new ArrayList<Chorbi>();
		Collection<Chorbi> listChorbiesByCity = new ArrayList<Chorbi>();
		Collection<Chorbi> chorbiMaxChirpsReceived = new ArrayList<Chorbi>();
		Collection<Chorbi> chorbiMaxChirpsSends = new ArrayList<Chorbi>();
		
		Collection<Chorbi> chorbiOrderByEvents = new ArrayList<Chorbi>();
		Collection<Manager> managerByEvents= new ArrayList<Manager>();
		Collection<Manager> allManagers= new ArrayList<Manager>();
		Collection<Chorbi> allChorbis = new ArrayList<Chorbi>();
		
		Collection<Integer> minStarsPerChorbi = likeService.minStarsByChorbi();
		Collection<Integer> maxStarsPerChorbi = likeService.maxStarsByChorbi();
		Collection<Double> avgStarsPerChorbi = likeService.avgStarsByChorbi();
		Collection<String> chorbiSortedByStars = likeService.findAllChorbiesSortedByAVGStars();
		
		
		Double minChorbiesAges = 0.0;
		Double maxChorbiesAges = 0.0;
		Double avgChorbiesAges = 0.0;
		Double ratioChorbiesWithCreditCard = 0.0;
		Double ratioChorbiesWithoutCreditCard = 0.0;
		Double ratioChorbiesFindActivities = 0.0;
		Double ratioChorbiesFindFriendship = 0.0;
		Double ratioChorbiesFindLove = 0.0;
		
		Double avgLikesPerChorbi, avgChirps = 0.0;
		int maxLikesPerChorbi = 0;
		int minLikesPerChorbi = 0;
		int minChirpsReceived, maxChirpsReceived, minChirpsSends, maxChirpsSends = 0;

		if (likeService.findAll().isEmpty()) {
			avgLikesPerChorbi = 0.0;
		} else {
			avgLikesPerChorbi = likeService.avgLikesPerChorbi();
		}
		if (chirpService.findAll().isEmpty()) {
			avgChirps = 0.0;
		} else {
			avgChirps = chirpService.avgChirps();
		}
		if (chirpService.findAll().isEmpty()) {
			minChirpsReceived = 0;
		} else {
			minChirpsReceived = chirpService.minChirpsReceived();
		}
		if (chirpService.findAll().isEmpty()) {
			maxChirpsReceived = 0;
		} else {
			maxChirpsReceived = chirpService.maxChirpsReceived();
		}
		if (chirpService.findAll().isEmpty()) {
			minChirpsSends = 0;
		} else {
			minChirpsSends = chirpService.minChirpsSends();
		}
		if (chirpService.findAll().isEmpty()) {
			maxChirpsSends = 0;
		} else {
			maxChirpsSends = chirpService.maxChirpsSends();
		}	
		if (chirpService.findAll().isEmpty()) {
			chorbiMaxChirpsReceived = new ArrayList<Chorbi>();
		} else {
			chorbiMaxChirpsReceived = chorbiService.maxChirpsReceived();
		}
		if (chirpService.findAll().isEmpty()) {
			chorbiMaxChirpsSends = new ArrayList<Chorbi>();
		} else {
			chorbiMaxChirpsSends = chorbiService.maxChirpsSends();
		}			
		
		Collection<Chorbi> chorbiesByLikes = new ArrayList<Chorbi>();

		if (chorbiService.findAll().isEmpty()) {
			listChorbiesByCountry = new ArrayList<Chorbi>();
		} else {
			listChorbiesByCountry = chorbiService.listChorbiesByCountry();
		}
		if (chorbiService.findAll().isEmpty()) {
			listChorbiesByCity = new ArrayList<Chorbi>();
		} else {
			listChorbiesByCity = chorbiService.listChorbiesByCity();
		}
		if (chorbiService.findAll().isEmpty()) {
			minChorbiesAges = 0.0;
		} else {
			minChorbiesAges = chorbiService.minChorbiesAges();
		}
		if (chorbiService.findAll().isEmpty()) {
			maxChorbiesAges = 0.0;
		} else {
			maxChorbiesAges = chorbiService.maxChorbiesAges();
		}
		if (chorbiService.findAll().isEmpty()) {
			avgChorbiesAges = 0.0;
		} else {
			avgChorbiesAges = chorbiService.avgChorbiesAges();
		}
		
		if (likeService.findAll().isEmpty()) {
			ratioChorbiesWithCreditCard = 0.0;
		} else {
			ratioChorbiesWithCreditCard = chorbiService.ratioChorbiesWithCreditCard();
		}
		if (likeService.findAll().isEmpty()) {
			ratioChorbiesWithoutCreditCard = 0.0;
		} else {
			ratioChorbiesWithoutCreditCard = chorbiService.ratioChorbiesWithoutCreditCard();
		}
		if (likeService.findAll().isEmpty()) {
			ratioChorbiesFindActivities = 0.0;
		} else {
			ratioChorbiesFindActivities = chorbiService.ratioChorbiesFindActivities();
		}
		if (likeService.findAll().isEmpty()) {
			ratioChorbiesFindFriendship= 0.0;
		} else {
			ratioChorbiesFindFriendship = chorbiService.ratioChorbiesFindFriendship();
		}
		if (likeService.findAll().isEmpty()) {
			ratioChorbiesFindLove= 0.0;
		} else {
			ratioChorbiesFindLove = chorbiService.ratioChorbiesFindLove();
		}
		
		if (likeService.findAll().isEmpty()) {
			avgLikesPerChorbi = 0.0;
		} else {
			avgLikesPerChorbi = likeService.avgLikesPerChorbi();
		}
		if (likeService.findAll().isEmpty()) {
			maxLikesPerChorbi = 0;
		} else {
			maxLikesPerChorbi = likeService.maxLikesPerChorbi();
		}
		if (likeService.findAll().isEmpty()) {
			minLikesPerChorbi = 0;
		} else {
			minLikesPerChorbi = likeService.minLikesPerChorbi();
		}

		if (chorbiService.findAll().isEmpty()) {
			chorbiesByLikes = new ArrayList<Chorbi>();
		} else {
			chorbiesByLikes = chorbiService.findChorbiesByLikes();
		}
		if (managerService.findAll().isEmpty()) {
			managerByEvents = new ArrayList<Manager>();
		} else {
			managerByEvents = managerService.managerByEvents();
		}
		if (managerService.findAll().isEmpty()) {
			allManagers = new ArrayList<Manager>();
		} else {
			allManagers = managerService.findAll();
		}
		if (chorbiService.findAll().isEmpty()) {
			chorbiOrderByEvents = new ArrayList<Chorbi>();
		} else {
			chorbiOrderByEvents = chorbiService.chorbiOrderByEvents();
		}
		if (chorbiService.findAll().isEmpty()) {
			allChorbis = new ArrayList<Chorbi>();
		} else {
			allChorbis = chorbiService.findAll();
		}
		if(minStarsPerChorbi.isEmpty()){
			minStarsPerChorbi = new ArrayList<Integer>();
		}
		if(maxStarsPerChorbi.isEmpty()){
			maxStarsPerChorbi = new ArrayList<Integer>();
		}
		if(avgStarsPerChorbi.isEmpty()){
			avgStarsPerChorbi = new ArrayList<Double>();
		}
		if(chorbiSortedByStars.isEmpty()){
			chorbiSortedByStars = new ArrayList<String>();
		}
		

		result = new ModelAndView("administrator/dashboard");
		result.addObject("avgLikesPerChorbi", avgLikesPerChorbi);
		result.addObject("maxLikesPerChorbi", maxLikesPerChorbi);
		result.addObject("minLikesPerChorbi", minLikesPerChorbi);
		result.addObject("chorbiesByLikes", chorbiesByLikes);
		
		result.addObject("minChirpsReceived", minChirpsReceived);
		result.addObject("maxChirpsReceived", maxChirpsReceived);
		result.addObject("avgChirps", avgChirps);
		result.addObject("minChirpsSends", minChirpsSends);
		result.addObject("maxChirpsSends", maxChirpsSends);
		result.addObject("chorbiMaxChirpsReceived", chorbiMaxChirpsReceived);
		result.addObject("chorbiMaxChirpsSends", chorbiMaxChirpsSends);
		
		result.addObject("listChorbiesByCountry", listChorbiesByCountry);
		result.addObject("listChorbiesByCity", listChorbiesByCity);
		result.addObject("minChorbiesAges", minChorbiesAges);
		result.addObject("maxChorbiesAges", maxChorbiesAges);
		result.addObject("avgChorbiesAges", avgChorbiesAges);
		result.addObject("ratioChorbiesWithCreditCard", ratioChorbiesWithCreditCard);
		result.addObject("ratioChorbiesWithoutCreditCard", ratioChorbiesWithoutCreditCard);
		result.addObject("ratioChorbiesFindActivities", ratioChorbiesFindActivities);
		result.addObject("ratioChorbiesFindFriendship", ratioChorbiesFindFriendship);
		result.addObject("ratioChorbiesFindLove", ratioChorbiesFindLove);
		result.addObject("managerByEvents",managerByEvents);
		result.addObject("chorbiOrderByEvents",chorbiOrderByEvents);
		result.addObject("allChorbis",allChorbis);
		result.addObject("allManagers",allManagers);
		
		
		
		result.addObject("minStarsByChorbi",minStarsPerChorbi);
		result.addObject("maxStarsByChorbi", maxStarsPerChorbi);
		result.addObject("avgStarsByChorbi",avgStarsPerChorbi);
		result.addObject("chorbiesSortedByAVGStars",chorbiSortedByStars);
		
		result.addObject("requestURI", "administrator/dashboard.do");


		return result;
	}
}