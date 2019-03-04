package com.codetest.userfile.util;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Component
public class CsvFileReaderImpl implements FileReader {

	private static final Logger logger = LoggerFactory.getLogger(CsvFileReaderImpl.class);

	@Override
	public <T> List<T> loadObjectList(Class<T> type, byte[] bytes) {

		try {
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withHeader();
	        CsvMapper mapper = new CsvMapper();
	        MappingIterator<T> readValues = 
	          mapper.readerFor(type).with(bootstrapSchema).readValues(bytes);
	        return readValues.readAll();
	    } catch (Exception e) {
	        logger.error("Error occurred while loading object list from file ", e);
	        return Collections.emptyList();
	    }
	}


}
