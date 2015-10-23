package com.thunderscore.intercom.mapper;

import java.io.IOException;
import java.io.Reader;

public class SingleLineIOReaderMapper extends IOReaderMapper<String>{

    public SingleLineIOReaderMapper(Reader reader, boolean ignoreReadError) {
        super(reader, ignoreReadError);
    }

    @Override
    protected String internalReadFromInput() throws IOException {
        String line = bufferedReader.readLine();
        if (line == null){
            isEOF = true;
        }
        return line;
    }
}
