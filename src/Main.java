import java.util.Scanner;
import java.util.*;
import java.util.ArrayList;
class Car {
    private String carID;

    private String brand;

    private String model;

    private double basePricePerDay;

    private boolean isAvailable;


    public Car(String carId, String brand, String model, double basePriceDay) {
        this.carID = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePriceDay;
        this.isAvailable = true;
    }
    public String getCarID()
    {
        return carID;
    }
    public String getBrand()
    {
        return brand;
    }
    public String getModel()
    {
        return model;
    }
    public double caluclatePrice(int rentalDays)
    {
        return basePricePerDay * rentalDays;
    }
    public boolean isAvailable()
    {
        return isAvailable;
    }
    public void rent()
    {
        isAvailable = false;
    }
    public void returnCar()
    {
        isAvailable = true;
    }
}
class Customer
{
    private String customerId;

    private String name;

    public Customer(String customerId,String name)
    {
        this.customerId =customerId;
        this.name = name;
    }
    public String getCustomerId()
    {
        return customerId;
    }
    public String getName()
    {
        return name;
    }
}
class Rental
{
    private Car car;

    private Customer customer;

    private  int days;

    public Rental(Car car , Customer customer , int days)
    {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }
    public Car getCar()
    {
        return car;
    }
    public Customer getCustomer()
    {
        return customer;
    }
    public int getDays()
    {
        return days;
    }
}
class CarRentalSystem
{
     private List<Car> cars;
     private List<Customer> customers;
     private List<Rental> rentals;

     public CarRentalSystem()
     {
         cars = new ArrayList<>();
         customers = new ArrayList<>();
         rentals = new ArrayList<>();
     }
     public void addCar(Car car)
     {
         cars.add(car);
     }
     public void addCustomer(Customer customer)
     {
         customers.add(customer);
     }
     public void rentCar(Car car,Customer customer,int days)
     {
         if(car.isAvailable()){
             car.rent();
             rentals.add(new Rental(car,customer,days));
         }
         else
         {
             System.out.println("Car is not available for rent .");
         }
     }
     public void returnCar(Car car) {
         car.returnCar();
         Rental rentalToRemove = null;
         for (Rental rental : rentals) {
             if (rental.getCar() == car) {
                 rentalToRemove = rental;
                 break;
             }
         }
     if(rentalToRemove != null) {
        rentals.remove(rentalToRemove);

    } else {
    System.out.println("Car was not rented.");
}}
     public void menu()
     {
         Scanner obj = new Scanner(System.in);
         while(true)
         {
             System.out.println("=====Car Rental System ====");
             System.out.println("1. Rent a Car");
             System.out.println("2. Return a Car");
             System.out.println("3. Exit ");
             System.out.println("Enter your choice : ");
             int choice  = obj.nextInt();
             obj.nextLine(); // Consume newLine

             if(choice == 1){
                 System.out.println("\n== Rent a Car ==\n");
                 System.out.println("Enter your name :");
                 String customerName = obj.nextLine();

                 System.out.println("\n Available Cars ");
                 for(Car car : cars){
                     if(car.isAvailable()){
                         System.out.println(car.getCarID() + "-"+car.getBrand() + " "+ car.getModel());
                     }
                 }
                 System.out.println("\nEnter the car Id you want to rent : ");
                 String carId = obj.nextLine();

                 System.out.println("Enter the number of days for rental: ");
                 int rentalDays = obj.nextInt();
                 obj.nextLine();

                 Customer newCustomer = new Customer("CUS" + (customers.size()+1),customerName);
                 addCustomer(newCustomer);

                 Car selectedCar = null;
                 for(Car car : cars){
                     if(car.getCarID().equals(carId) && car.isAvailable())
                     {
                         selectedCar= car;
                         break;
                     }
                 }
                 if(selectedCar != null) {
                     double totalPrice = selectedCar.caluclatePrice(rentalDays);
                     System.out.println("\n == Rental Information ==\n");
                     System.out.println("Customer ID:" + newCustomer.getCustomerId());
                     System.out.println("Customer Name:" + newCustomer.getName());
                     System.out.println("Car:" + selectedCar.getBrand() + " " + selectedCar.getModel());
                     System.out.println("Rental Days: " + rentalDays);
                     System.out.printf("Total Price: $%.2f%n", totalPrice);

                     System.out.println("\n Confirm rental (Y/N): ");
                     String confirm = obj.nextLine();

                     if (confirm.equalsIgnoreCase("Y")) {
                         rentCar(selectedCar, newCustomer, rentalDays);
                         System.out.println("\nCar rented successfully");
                     } else {
                         System.out.println("\n Rental Cancelled. ");
                     }
                     } else {
                         System.out.println("\nInvalid car selection or Car not available for rent.");
                     }
                 } else if (choice == 2) {
                 System.out.println("\n == Return a Car == \n");
                 System.out.println("Enter the car Id you want to return ");
                 String carId = obj.nextLine();

                 Car carToReturn = null;
                 for(Car car : cars){
                     if(car.getCarID().equals(carId) && !car.isAvailable()) {
                         carToReturn = car;
                         break;
                     }
                 }
                 if(carToReturn != null) {
                     Customer customer = null;
                     for(Rental rental : rentals){
                         if(rental.getCar() == carToReturn){
                             customer = rental.getCustomer();
                             break;
                         }
                     }

                     if(customer != null) {
                         returnCar(carToReturn);
                         System.out.println("Car returned successfully by " + customer.getName());
                     } else {
                         System.out.println("Car was not rented or rental information is misssing");
                     }
                 } else {
                     System.out.println("Invalid car ID or Car is not rented");
                 }
             } else if(choice == 3){
                 break;
             } else {
                 System.out.println("Invalid choice.Please enter a valid option");
             }
         }
         System.out.println(" \n Thank you for using the Car Rental System!");
         }
     }
public class Main
{
    public static void main(String[] args)
    {
        CarRentalSystem ob = new CarRentalSystem();
        Car car1 = new Car("COO1","Toyota","Camry",60.0);
        Car car2 = new Car("C002","HondaCity","Accord",70.0);
        Car car3 = new Car("C003","Mahindra","Thar",150.0);
        ob.addCar(car1);
        ob.addCar(car2);
        ob.addCar(car3);
        ob.menu();
    }
}