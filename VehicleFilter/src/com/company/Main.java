package com.company;
import java.util.List;
import java.util.ArrayList;

class Vehicle{

    private String make;
    private String model;
    private String bodyStyle;
    private String driveType;
    private String fuelType;
    private int cylinders;

    public Vehicle(String make, String model, String bodyStyle, String driveType, String fuelType, int cylinders) {
        this.make = make;
        this.model = model;
        this.bodyStyle = bodyStyle;
        this.driveType = driveType;
        this.fuelType = fuelType;
        this.cylinders = cylinders;
    }

    public String getMake() {
        return make;
    }
    public String getModel() {
        return model;
    }
    public String getBodyStyle() {
        return bodyStyle;
    }
    public String getDriveType() {
        return driveType;
    }
    public String getFuelType() {
        return fuelType;
    }
    public int getCylinders() {
        return cylinders;
    }
}

interface Criteria {
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles);
}

class CriteriaSedan implements Criteria {

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> sedanVehicles = new ArrayList<Vehicle>();

        for (Vehicle vehicle : vehicles) {
            if(vehicle.getBodyStyle().equalsIgnoreCase("SEDAN")){
                sedanVehicles.add(vehicle);
            }
        }
        return sedanVehicles;
    }
}

class CriteriaSUV implements Criteria {

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> suvVehicles = new ArrayList<Vehicle>();

        for (Vehicle vehicle : vehicles) {
            if(vehicle.getBodyStyle().equalsIgnoreCase("SUV")){
                suvVehicles.add(vehicle);
            }
        }
        return suvVehicles;
    }
}

class CriteriaFourWheelDrive implements Criteria {

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> fourWheelDriveVehicles = new ArrayList<Vehicle>();

        for (Vehicle vehicle : vehicles) {
            if(vehicle.getDriveType().equalsIgnoreCase("4WD")){
                fourWheelDriveVehicles.add(vehicle);
            }
        }
        return fourWheelDriveVehicles;
    }
}

class CriteriaToyota implements Criteria {

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> toyotaVehicles = new ArrayList<Vehicle>();

        for (Vehicle vehicle : vehicles) {
            if(vehicle.getMake().equalsIgnoreCase("TOYOTA")){
                toyotaVehicles.add(vehicle);
            }
        }
        return toyotaVehicles;
    }
}

class CriteriaFord implements Criteria {

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> fordVehicles = new ArrayList<Vehicle>();

        for (Vehicle vehicle : vehicles) {
            if(vehicle.getMake().equalsIgnoreCase("FORD")){
                fordVehicles.add(vehicle);
            }
        }
        return fordVehicles;
    }
}

class CriteriaFourCyl implements Criteria {

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> fourCylVehicles = new ArrayList<Vehicle>();

        for (Vehicle vehicle : vehicles) {
            if(vehicle.getCylinders() == 4){
                fourCylVehicles.add(vehicle);
            }
        }
        return fourCylVehicles;
    }
}

class AndCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public AndCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {

        List<Vehicle> firstCriteriaVehicles = criteria.meetCriteria(vehicles);
        return otherCriteria.meetCriteria(firstCriteriaVehicles);
    }
}

class OrCriteria implements Criteria {

    private Criteria criteria;
    private Criteria otherCriteria;

    public OrCriteria(Criteria criteria, Criteria otherCriteria) {
        this.criteria = criteria;
        this.otherCriteria = otherCriteria;
    }

    @Override
    public List<Vehicle> meetCriteria(List<Vehicle> vehicles) {
        List<Vehicle> firstCriteriaItems = criteria.meetCriteria(vehicles);
        List<Vehicle> otherCriteriaItems = otherCriteria.meetCriteria(vehicles);

        for (Vehicle vehicle : otherCriteriaItems) {
            if(!firstCriteriaItems.contains(vehicle)){
                firstCriteriaItems.add(vehicle);
            }
        }
        return firstCriteriaItems;
    }
}

public class Main {

    public static void main(String[] args) {
        List<Vehicle> vehicles = new ArrayList<Vehicle>();

        vehicles.add(new Vehicle("Ford","Mustang", "Coupe","RWD", "gasoline",8));
        vehicles.add(new Vehicle("Ford","F150", "Truck","4WD", "gasoline",8));
        vehicles.add(new Vehicle("Ford","Ranger", "Truck","4WD", "gasoline",4));
        vehicles.add(new Vehicle("Ford","Explorer", "SUV","4WD", "gasoline",6));
        vehicles.add(new Vehicle("Toyota","Camry", "Sedan","FWD", "gasoline",4));
        vehicles.add(new Vehicle("Toyota","Corolla", "Sedan","FWD", "gasoline",4));
        vehicles.add(new Vehicle("Toyota","4Runner", "SUV","4WD", "gasoline",6));
        vehicles.add(new Vehicle("Toyota","Rav4", "SUV","4WD", "gasoline",4));
        vehicles.add(new Vehicle("Volkswagen","Jetta", "Sedan","FWD", "diesel",4));
        vehicles.add(new Vehicle("Mercedes","Sprinter", "Van","RWD", "diesel",5));
        vehicles.add(new Vehicle("Cadillac","Escalade", "SUV","4WD", "gasoline",8));


        Criteria sedan = new CriteriaSedan();
        Criteria fourWheelDrive = new CriteriaFourWheelDrive();
        Criteria fourCyl = new CriteriaFourCyl();
        Criteria suv = new CriteriaSUV();
        Criteria toyota = new CriteriaToyota();
        Criteria ford = new CriteriaFord();
        Criteria fordAndFourWheelDrive = new AndCriteria(ford, fourWheelDrive);
        Criteria toyotaOrFord = new OrCriteria(toyota, ford);
        Criteria toyotaAndFourCyl = new AndCriteria(toyota, fourCyl);

        System.out.println("\nSedans: ");
        printVehicles(sedan.meetCriteria(vehicles));

        System.out.println("\nSUVs: ");
        printVehicles(suv.meetCriteria(vehicles));

        System.out.println("\nFour Wheel Drive Vehicles: ");
        printVehicles(fourWheelDrive.meetCriteria(vehicles));

        System.out.println("\nFour Wheel Drive Fords: ");
        printVehicles(fordAndFourWheelDrive.meetCriteria(vehicles));

        System.out.println("\nToyota Or Ford: ");
        printVehicles(toyotaOrFord.meetCriteria(vehicles));

        System.out.println("\nFour Cylinder Toyotas: ");
        printVehicles(toyotaAndFourCyl.meetCriteria(vehicles));
    }

    public static void printVehicles(List<Vehicle> vehicles){

        for (Vehicle vehicle : vehicles) {
            System.out.println(vehicle.getMake() + " " + vehicle.getModel() + ", Body Style: " + vehicle.getBodyStyle() +
                    ", Drive: " + vehicle.getDriveType() + ", Fuel: " + vehicle.getFuelType() + ", Cylinders: " + vehicle.getCylinders());
        }
    }
}
