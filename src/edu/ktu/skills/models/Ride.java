package edu.ktu.skills.models;

import java.util.Objects;

public class Ride {

    private int id;

    private Location start;
    private Location end;

    private int startTime;
    private int endTime;

    private int evaluatedScore = 0;

    private Vehicle assignedVehicle = null;

    public Ride(int id, Location start, Location end, int startTime, int endTime) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getId() {
        return id;
    }

    public Location getStart() {
        return start;
    }

    public void setEvaluatedScore(int evaluatedScore) {
        this.evaluatedScore = evaluatedScore;
    }

    public int getEvaluatedScore() {
        return evaluatedScore;
    }

    public void setStart(Location start) {
        this.start = start;
    }

    public Location getEnd() {
        return end;
    }

    public void setEnd(Location end) {
        this.end = end;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTime() {
        return endTime;
    }

    public void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    public Vehicle getAssignedVehicle() {
        return assignedVehicle;
    }

    public void setAssignedVehicle(Vehicle assignedVehicle) {
        this.assignedVehicle = assignedVehicle;
    }

    public int getDistance(){
        return start.distance(end);
    }

    public int maxDelay(){
        return endTime - startTime - getDistance();
    }

    @Override
    public String toString() {
        return "Ride{" +
                "start=" + start +
                ", end=" + end +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ride ride = (Ride) o;
        return id == ride.id;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
