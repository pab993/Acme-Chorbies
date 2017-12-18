package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.LikeRepository;
import domain.Like;

@Component
@Transactional
public class StringToLikeConverter implements Converter<String, Like> {
	@Autowired LikeRepository customerRepository;
	@Override
	public Like convert(String text) {
		// TODO Auto-generated method stub
		Like result;
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
