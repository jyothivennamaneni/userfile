package com.codetest.userfile;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.codetest.userfile.controlller.UserFileController;
import com.codetest.userfile.model.User;
import com.codetest.userfile.repository.UserRepository;
import com.codetest.userfile.util.CsvFileReaderImpl;
import com.codetest.userfile.util.FileReader;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserFileControllerTest {
	private static final Logger logger = LoggerFactory.getLogger(UserFileControllerTest.class);

	@Mock CsvFileReaderImpl reader;
	
	@Mock UserRepository repo;
	
	@InjectMocks UserFileController controller;
	
	
	@Mock MultipartFile file;
	
	@Test
	public void testUploadFile() throws IOException {
		List<User> users = new ArrayList<User>();
		users.add(new User(new Long(1), "Peter", "Wilkins", "pw@abc.com", "1212121212"));
		
		when(file.getOriginalFilename()).thenReturn("test.csv");

		when(reader.loadObjectList(ArgumentMatchers.<Class<User>>any(), ArgumentMatchers.any())).thenReturn(users);
		when(repo.saveAll(Mockito.anyList())).thenReturn(users);
		ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);
		try {
			r = controller.handleUserFile(file);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		
		assertEquals(r.getStatusCode().OK.toString(),"200 OK");
		
	}

	@Test
	public void testUploadFile_error() throws IOException {
		List<User> users = new ArrayList<User>();
		users.add(new User(new Long(1), "Peter", "Wilkins", "pwabc.com", "121212"));
		when(file.getOriginalFilename()).thenReturn("test.csv");

		when(reader.loadObjectList(ArgumentMatchers.<Class<User>>any(), Mockito.any(byte[].class))).thenReturn(users);
		when(repo.saveAll(Mockito.anyList())).thenReturn(users);
		ResponseEntity r = new ResponseEntity(HttpStatus.NO_CONTENT);
		try {
			r = controller.handleUserFile(file);
		}catch(Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();

		}
		
		assertEquals(r.getStatusCode().toString(),"200 OK");
		
	}
}
