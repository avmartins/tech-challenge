package pixeon.tech.challenge.exception;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ExamNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1032664457746601907L;
	
	private static final Logger logger = Logger.getLogger(ExamNotFoundException.class);
	
	private String id;

	public ExamNotFoundException(String id) {
		super(String.format(" Exam não existe : '%s'", id));
		this.id = id;
		logger.error(String.format(" Exam não existe : '%s'", id));
	}

	public String getId() {
		return this.id;
	}

}
