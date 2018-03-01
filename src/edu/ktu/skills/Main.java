package edu.ktu.skills;

import edu.ktu.skills.models.Location;
import edu.ktu.skills.models.Ride;
import edu.ktu.skills.models.Vehicle;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Main implements Runnable {

    private static final String[] INPUT_FILES = new String[]{
            "a_example.in",
            "b_should_be_easy.in",
            "c_no_hurry.in",
            "d_metropolis.in",
            "e_high_bonus.in"
    };

    private int rows;
    private int columns;

    private int onTimeBonus;
    private int stepsCount;

    private List<Vehicle> vehicles;
    private List<Ride> rides;

    private String inputFile;

    public Main(String inputFile) {
        this.inputFile = inputFile;
    }

    @Override
    public void run() {
        try {
            System.out.printf("-- Reading data file %s --\n", inputFile);
            read(inputFile);
            System.out.printf("-- Read data success %s --\n", inputFile);

            System.out.printf("-- Running program %s --\n", inputFile);
            calculate();

            System.out.printf("-- Writing output to file %s --\n", inputFile);
            write(inputFile + ".out");
            System.out.printf("-- Program end %s --\n", inputFile);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    void calculate() {

        for (Vehicle vehicle : vehicles) {
            iteration(vehicle, 0);
        }

        for (int time = 0; time < stepsCount; time++) {
            for (Vehicle vehicle : vehicles) {
                if (vehicle.getCurrentItteration() > time) {
                    continue;
                }
                iteration(vehicle, time);
            }
        }


    }

    void iteration(Vehicle vehicle, int currentTime) {
        List<Ride> availableRides = new ArrayList<>();
        for (Ride ride : rides) {
            if (ride.getAssignedVehicle() != null) {
                continue;
            }
            if (ride.getEndTime() <= currentTime) {
                continue;
            }
            if (currentTime + ride.getDistance() + vehicle.getLocation().distance(ride.getStart()) > ride.getEndTime()) {
                continue;
            }
            availableRides.add(ride);
        }
        if (availableRides.size() == 0) {
            return;
        }
        availableRides.sort(new Comparator<Ride>() {
            @Override
            public int compare(Ride o1, Ride o2) {
                return Integer.compare(evaluateRideScore(vehicle, currentTime, o1), evaluateRideScore(vehicle, currentTime, o2));
            }
        });
        Ride ride = availableRides.get(0);
        int deltaTime = vehicle.getLocation().distance(ride.getStart()) + ride.getDistance();
        if (currentTime + vehicle.getLocation().distance(ride.getStart()) < ride.getStartTime()) {
            deltaTime += ride.getStartTime() - (currentTime + vehicle.getLocation().distance(ride.getStart()));
        }
        if (ride.getAssignedVehicle() != null) {
            ride.getAssignedVehicle().removeRide(ride);
        }
        vehicle.assignRide(ride);
        vehicle.setLocation(ride.getEnd());
        vehicle.setCurrentItteration(currentTime + deltaTime);
//        iteration(vehicle, currentTime + deltaTime);

    }

    private int evaluateRideScore(Vehicle vehicle, int currentTime, Ride ride) {
        int delay;
        int early;
        delay = Math.abs(ride.getStartTime() -
                currentTime + vehicle.getLocation().distance(ride.getStart()));
        early = ride.getEndTime() - currentTime + ride.getDistance() + vehicle.getLocation().distance(ride.getStart());
        int distance = ride.getStart().distance(vehicle.getLocation());
        int score = distance + delay + early - (delay == 0 ? onTimeBonus : 0);
        return score;
    }

    void read(String file) throws IOException {
        rides = new ArrayList<>();
        FileInputStream stream = new FileInputStream(file);
        Scanner scanner = new Scanner(stream);

        rows = scanner.nextInt();
        columns = scanner.nextInt();

        int vehicleCount = scanner.nextInt();
        int ridesCount = scanner.nextInt();
        onTimeBonus = scanner.nextInt();
        stepsCount = scanner.nextInt();

        //Initialize vehicles
        vehicles = new ArrayList<>();
        for (int i = 1; i <= vehicleCount; i++) {
            vehicles.add(new Vehicle(i));
        }

        //Read rides data
        for (int r = 0; r < ridesCount; r++) {
            int a = scanner.nextInt(); //row
            int b = scanner.nextInt(); //column
            int x = scanner.nextInt(); //row
            int y = scanner.nextInt(); //column
            int s = scanner.nextInt(); //start
            int f = scanner.nextInt(); //finish

            Ride ride = new Ride(r, new Location(a, b), new Location(x, y), s, f);
            rides.add(ride);
        }

        scanner.close();
    }

    void write(String file) throws IOException {
        FileWriter writer = new FileWriter(file);

        for (Vehicle vehicle : vehicles) {
            writer.write(vehicle.getAssignedRides().size() + " ");
            for (Ride ride : vehicle.getAssignedRides()) {
                writer.write(ride.getId() + " ");
            }
            writer.write("\n");
        }

        writer.close();
    }

    public static void main(String[] args) {
        List<Thread> threads = new ArrayList<>();
        for (String inputFile : INPUT_FILES) {
            Thread thread = new Thread(new Main(inputFile), inputFile);
            threads.add(thread);
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println("Interrupted thread");
            }
        }
    }


}
