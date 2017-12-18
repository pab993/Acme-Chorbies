package converters;

import javax.transaction.Transactional;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import domain.Like;

@Component
@Transactional
public class LikeToStringConverter implements Converter<Like, String>{

	@Override
	public String convert(Like customer) {
		// TODO Auto-generated method stub
		String result;
		if(customer == null){
			result = null;
		}else{
			result = String.valueOf(customer.getId());
		}
		return result;
	}

}
