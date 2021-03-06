package com.codetest.userfile.util;

/**
 * 
 * @author jyothivennamaneni
 * Factory class that has a method to create FileReader type based on FileType input
 */
public class FileReaderFactory {
	public enum FileType{	
		CSV("csv");//,XSL("xsl"); currently only one type implemented but can easily be enhanced to include other types
		
		private final String type;
		
		FileType(String type){
			this.type = type;
		}
		
		@Override
		public String toString() {
			return type;
		}
		

		public static FileType fromString(String fileType) {
			for (FileType t : FileType.values()) {
	            if (t.type.equalsIgnoreCase(fileType)) {
	                return t;
	            }
	        }
	        return null;
		}
	}
	public static FileReader getReaderInstance(FileType type) {
		FileReader reader = null;
		switch(type) {
		case CSV:
			reader = new CsvFileReaderImpl();
			break;
		}
		
		return reader;
	}

}
