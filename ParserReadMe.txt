##################################################################################################
##################################################################################################

CONTENT:
- Architecture of the parser
	- What files are part of the parser system
	- How do the files interact
- How import a file into the application

##################################################################################################

ARCHITECTURE OF THE PARSER
	What files are part of the parser system?
		The code of the parser is in the two files 
			src/amdb/client/ParserClientside.java 
		and 
			src/amdb/preprocessing/ParserPreprocessing.java

		A JUnit test covering these two files is in
			/test/amdb/ParserClientsidePreprocessingTest.java

		The parser reads files from the folder 
			war/WEB-INF/files
		Preprocessed results (see "How do these files interact?") are found in 
			war/PreprocessedData/movies_preprocessed_dir.tsv

		The JUnit test reads from 
			test/amdb/testData/test_file.txt
		and writes to 
			test/amdb/testData/parsertestresult.tsv.

	How do the files interact?
		The parsing process consists of two phases: preprocessing and clientside parsing.

		In the preprocessing phase source files from a folder are parsed and the relevant data 
		is extracted. The relevant data is concatenated for all source files and written to a 
		new file in war/PreprocessedData/movies_preprocessed_dir.tsv
		The extracted data is converted to the following format:
			name	releaseDate	length	lang1|...|langN	country1|...|countryN	genre1|...|genreN
		Where each field of values is separated by a tab and elements of lists are separated by
		pipes.

		The clientside parsing phase starts when the client requests the entire preprocessed file
		by AJAX. After the file is received, the ParserClientside method 
		stringToMovieCollection is used to convert the received string to a Moviebase-Object.
		The entire database then resides in the memory of the client.

##################################################################################################

HOW IMPORT A FILE INTO THE APPLICATION
	To import a new file (formatted like the original files), it has to be added to the folder
		war/WEB-INF/files

	Changes will only apply if after that, the main-Method of 
		src/amdb/preprocessing/ParserPreprocessing.java
	is run. After that, the application can be deployed with the new data.

##################################################################################################
##################################################################################################

