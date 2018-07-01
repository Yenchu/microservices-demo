package demo.ms.web.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception e) {
		String body = e.getMessage();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
		
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(body, headers, status);
		return responseEntity;
	}
}
