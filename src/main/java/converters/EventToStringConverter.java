package converters;


import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Event;
@Component
@Transactional
public class EventToStringConverter implements Converter<Event, String>{

	@Override
	public String convert(Event actor) {
		
		String result;
		if(actor == null){
			result = null;
		}else{
			result = String.valueOf(actor.getId());
		}
		return result;
	}

}