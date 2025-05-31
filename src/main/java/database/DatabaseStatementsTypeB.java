package database;

public class DatabaseStatementsTypeB {
    public String returnBaseInsert (){
        return """
                insert into typeB (ID, date, amount, amount1, amount2)
                values (?, ?, ?, ?, ?);
                """;
    }
    @Override
    public String toString(){
        return "Database actions to complete end-to-end load";
    }
}
