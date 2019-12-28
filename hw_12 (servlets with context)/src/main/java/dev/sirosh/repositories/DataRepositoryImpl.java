package dev.sirosh.repositories;

public class DataRepositoryImpl implements DataRepository {
    public void save(String data) {
        System.out.println("In DataRepository " + data);
    }
}
