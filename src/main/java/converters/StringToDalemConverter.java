package converters;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import repositories.DalemRepository;
import domain.Dalem;

@Component
@Transactional
public class StringToDalemConverter implements Converter<String, Dalem> {

	@Autowired
	DalemRepository	dalemRepository;


	@Override
	public Dalem convert(String text) {
		Dalem result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = dalemRepository.findOne(id);
			}
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}