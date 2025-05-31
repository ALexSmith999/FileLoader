Trace, parse, validate and load into a database a range of files with predefined contracts.

To run the program include in src/main/resources/config.properties :
    BATCH_SIZE=
    DB_USER=
    DB_PASSWD=
    FILES_DIRECTORY=
    DB_LINK=
To add the load of a new file add a batch, validation, parse, and load components 
or whatever you need, using loader.Build() class

Add / remove statements / procedures in a DatabaseStatements part.
