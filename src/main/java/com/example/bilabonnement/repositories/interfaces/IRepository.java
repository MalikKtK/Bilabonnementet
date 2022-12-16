package com.example.bilabonnement.repositories.interfaces;
//Udarbejdet af Malik Kütük
import java.util.List;

public interface IRepository<T> {
    //Create
    public T create(T entity);

    //Read
    public T getCarByID(int id);


    public List<T> getAllCars();

    //Update
    public T update(T entity);

    //Delete
    public boolean deleteById(int id);
}
