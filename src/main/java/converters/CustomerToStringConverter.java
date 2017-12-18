package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Chorbi;

@Component
@Transactional
public class CustomerToStringConverter implements Converter<Chorbi, String>{

	@Override
	public String convert(Chorbi customer) {
		
		String result;
		if(customer == null){
			result = null;
		}else{
			result = String.valueOf(customer.getId());
		}
		return result;
	}

}