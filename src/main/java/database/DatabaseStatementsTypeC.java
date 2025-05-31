package database;

public class DatabaseStatementsTypeC {
    public String returnBaseInsert (){
        return """
                insert into typeC(ID, name, surname, city)
                values (?, ?, ?, ?);
                """;
    }
    @Override
    public String toString(){
        return "Database actions to complete end-to-end load";
    }
}
