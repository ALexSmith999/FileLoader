package database;

import file.Entities;

public class Insertion {
    public String returnQuery (Entities query){
        return switch (query) {
            case TYPEA -> {
                yield """
                    insert into typeA (ID, date, amount)
                    values (?, ?, ?);
                    """;
            }
            case TYPEB -> {
                yield """
                    insert into typeB (ID, date, amount, amount1, amount2)
                    values (?, ?, ?, ?, ?);
                    """;
            }
            case TYPEC -> {
                yield """
                    insert into typeC(ID, name, surname, city)
                    values (?, ?, ?, ?);
                    """;
            }
            default -> {
                yield "";
            }
        };
    }
}
