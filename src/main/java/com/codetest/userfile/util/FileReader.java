package com.codetest.userfile.util;

import java.util.List;

public interface FileReader  {
	
	<T> List<T> loadObjectList(Class<T> type,byte[] bytes);
	

}
