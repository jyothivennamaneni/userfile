package com.codetest.userfile;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.codetest.userfile.model.User;
import com.codetest.userfile.util.CsvFileReaderImpl;

public class TestCsvFileReaderImpl {

	StringBuilder b = new StringBuilder().append("firstName,lastName,email,phoneNumber").append(System.lineSeparator()) 
			.append("Sai,Ch,saich@test.com,1122334455").append(System.lineSeparator())
			.append("Jyo,Ve,JyoVe@test.com,2233445566").append(System.lineSeparator())
			.append("Aadhya,Ch,A@F.C,8273923828").append(System.lineSeparator());
	
	
	@Test
	public void testLoadObjectList() {
		
		List<User> userList = new CsvFileReaderImpl().loadObjectList(User.class, b.toString().getBytes());
		assertEquals(3, userList.size());
		
	}
	
}
