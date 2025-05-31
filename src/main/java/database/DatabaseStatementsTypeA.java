package database;

public class DatabaseStatementsTypeA {
    public String returnBaseInsert (){
        return """
                insert into typeA (ID, date, amount)
                values (?, ?, ?);
                """;
    }
    @Override
    public String toString(){
        return "Database actions to complete end-to-end load";
    }
}
