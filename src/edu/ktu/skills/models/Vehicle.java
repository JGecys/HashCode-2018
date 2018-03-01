package edu.ktu.skills.models;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {

    private int id;

    private Location location;

    private int currentItteration = 0;

    private List<Ride> assignedRides = new ArrayList<>();

    public Vehicle(int id) {
        this.id = id;
        location = new Location();
    }

    public int getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public List<Ride> getAssignedRides() {
        return assignedRides;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getCurrentItteration() {
        return currentItteration;
    }

    public void setCurrentItteration(int currentItteration) {
        this.currentItteration = currentItteration;
    }

    public void assignRide(Ride ride){
        if(ride.getAssignedVehicle() != null){
            throw new RuntimeException("Cannot have 2 vehicles assigned");
        }
        assignedRides.add(ride);
        ride.setAssignedVehicle(this);
    }

    public void removeRide(Ride ride){
        if(ride.getAssignedVehicle() == null){
            throw new RuntimeException("Cannot remove unassigned ride");
        }
        assignedRides.remove(ride);
        ride.setAssignedVehicle(null);

    }

}
