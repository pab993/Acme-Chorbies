package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.ChorbiRepository;
import domain.Chorbi;

@Component
@Transactional
public class StringToCustomerConverter implements Converter<String, Chorbi>{
@Autowired ChorbiRepository customerRepository;

	@Override
	public Chorbi convert(String text) {
		Chorbi result;
		int id;
		
		try{
			if(StringUtils.isEmpty(text)){
				result = null;
			}else{
				id = Integer.valueOf(text);
				result = customerRepository.findOne(id);
			}
		}catch(Throwable oops){
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
