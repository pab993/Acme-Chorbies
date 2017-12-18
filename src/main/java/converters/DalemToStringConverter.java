package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Dalem;

@Component
@Transactional
public class DalemToStringConverter implements Converter<Dalem, String>{

	@Override
	public String convert(Dalem dalem) {
		
		String result;
		if(dalem == null){
			result = null;
		}else{
			result = String.valueOf(dalem.getId());
		}
		return result;
	}
}