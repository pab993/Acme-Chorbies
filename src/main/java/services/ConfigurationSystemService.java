package services;


import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationSystemRepository;
import domain.Administrator;
import domain.ConfigurationSystem;

@Transactional
@Service
public class ConfigurationSystemService {
	
	@Autowired
	private ConfigurationSystemRepository configurationSystemRepository;
	
	@Autowired
	private ActorService actorService;
	
	public ConfigurationSystemService(){
		super();
	}
	
	public ConfigurationSystem findOne(int configurationSystemId) {
		Assert.notNull(configurationSystemId);

		ConfigurationSystem result;

		result = configurationSystemRepository.findOne(configurationSystemId);
		Assert.notNull(result);

		return result;
	}
	
	public Collection<ConfigurationSystem> findAll() {

		Collection<ConfigurationSystem> configurationSystems;

		configurationSystems = configurationSystemRepository.findAll();
		Assert.notNull(configurationSystems);

		return configurationSystems;
	}
	
	public ConfigurationSystem create(){
		ConfigurationSystem result = new ConfigurationSystem();
		return result;
		
	}
	
	public ConfigurationSystem save(ConfigurationSystem configurationSystem){
		Assert.notNull(configurationSystem);
		Administrator principal = (Administrator) actorService.findByPrincipal();
		Assert.notNull(principal);
		
		ConfigurationSystem result = configurationSystemRepository.save(configurationSystem);
		return result;
		
		
	}
	
	public ConfigurationSystem getCS(){
		Collection<ConfigurationSystem> configurationSystems;

		configurationSystems = this.findAll();
		return configurationSystems.iterator().next();
	}

	public Double selectChorbiFee() {
		ConfigurationSystem cs = this.getCS();
		return cs.getChorbiFee();
	}
	
//	public List<FieldError> comprobarErrores(ConfigurationSystem configurationSystem, String nombreObjeto, String modificador) {
//		List<FieldError> errores = new ArrayList<FieldError>();;
//
//		if (!(configurationSystem.getChorbiFee() instanceof Double)) {
//			String[] codigos = {"chorbi.fee.error"};
//			FieldError error = new FieldError(nombreObjeto, modificador + "ChorbiFee", configurationSystem.getChorbiFee(), false, codigos, null, "");
//			errores.add(error);
//		}
//
//		return errores;
//	}
}