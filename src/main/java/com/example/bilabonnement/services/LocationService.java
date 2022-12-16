package com.example.bilabonnement.services;
//Udarbejdet af Malik Kütük
import com.example.bilabonnement.models.Location;
import com.example.bilabonnement.repositories.LocationRepository;

import java.util.List;

public class LocationService {

    LocationRepository locationRepository = new LocationRepository();

    // Får fat i lokation. Lokation er de to forskellige steder vi har. HQ og forhandler
    public List<Location> getAllLocations() {
        return locationRepository.getAllCars();
    }
}
