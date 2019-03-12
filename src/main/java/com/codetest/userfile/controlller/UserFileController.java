package com.codetest.userfile.controlller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.codetest.userfile.model.User;
import com.codetest.userfile.repository.UserRepository;
import com.codetest.userfile.util.FileReader;
import com.codetest.userfile.util.FileReaderFactory;
import com.codetest.userfile.util.FileReaderFactory.FileType;

@RestController
@CrossOrigin(origins = {"*"},
maxAge = 4800, allowCredentials = "true") 
public class UserFileController {


	@Autowired
	private UserRepository repo;
	

	private static final Logger logger = LoggerFactory.getLogger(UserFileController.class);

	/**
	 * returns response code 200, true
	 * prints the username who is logged in, using the Authentication passed from spring security
	 * @param auth
	 * @return
	 */
	
	@PostMapping("/login")
	public ResponseEntity<?> login(Authentication auth) {

		logger.info("logged in user:" + auth.getName());
		return ResponseEntity.ok().body(true);

	}

	
	
	/**
	 * Controller method that orchestrates: 
	 * 1) converting the file content to List of users
	 * 2) validating file data
	 * 3) loading the data in memory database
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/users")
	public ResponseEntity<?> handleUserFile(@RequestParam("file") MultipartFile file) throws Exception {

		logger.info(file.getOriginalFilename());
		List<User> userList = null;
		String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf(".")+1);
		
		FileReader reader = FileReaderFactory.getReaderInstance(FileType.fromString(fileType.toString()));
		try {
			 userList = reader.loadObjectList(User.class, file.getBytes());
			 if(logger.isDebugEnabled()){
				 logger.debug("size of list" + userList.size());
			 }
			return handleUserList(userList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error("exception", e);
			throw e;
			
		}
		catch (Exception ex) {
			// TODO Auto-generated catch block
			logger.error("exception", ex);
			throw ex;
			
		}


	}

	/**
	 * private method that handles validation, persistence
	 * @param userList
	 * @return
	 */
	
	public  ResponseEntity<?> handleUserList( List<User> userList) {
		logger.info(userList.toString());
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	      Validator validator = factory.getValidator();

	      Set<ConstraintViolation<User>> errors = userList.stream()
	    		  								.map(item -> validator.validate(item))
	    		  								.flatMap(s -> s.stream()).collect(Collectors.toSet());

	      List<String> messages = new ArrayList<String>();
	      
	      if(errors.size()>0) {
	      errors.forEach((e) -> {
		      StringBuilder builder = new StringBuilder();
	    	  messages.add(builder.append( "The value [").append(e.getInvalidValue()).append("] is invalid, ").append( e.getMessage()) .append(". Error row:").append( e.getLeafBean()).append(System.lineSeparator()).toString());
		      logger.error(builder.toString());
	      });
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(messages);
	      }
		repo.saveAll(userList);
		return ResponseEntity.status(HttpStatus.OK).body(userList);
	}
	
	
}
