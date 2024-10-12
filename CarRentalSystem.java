package com.consolebasedproject;

import java.util.*;
import java.util.ArrayList;

public class CarRentalSystem {
	private ArrayList<Car> cars;
	private ArrayList<Customer> customers;
	private ArrayList<Rental> rentals;
	
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
	
	public void rentCar(Car car ,Customer customer ,int days)
	{
		if(car.isAvailable())
		{
			car.rent();
			rentals.add(new Rental(car ,customer ,days));
		}
		else
		{
			System.out.println("Car is not available for rent.");
		}
	}
	
	public void returnCar(Car car)
	{
		car.returnCar();
		Rental rentalToRemove = null;
		for(Rental rental : rentals)
		{
			if(rental.getcar() == car)
			{
				rentalToRemove = rental;
				break;
			}
		}
		if(rentalToRemove != null)
		{
			rentals.remove(rentalToRemove);
		}
		else
		{
			System.out.println("Car was not rented.");
		}
	}
	
	public void menu()
	{
		Scanner riddhi = new Scanner(System.in);
		while(true)
		{
			System.out.println("========================= Car Rental System ============================");
			System.out.println("1.Rent a car");
			System.out.println("2.Return a car");
			System.out.println("3.Exit");
			System.out.println("Enter your choice :");
			
			int choice = riddhi.nextInt();
			riddhi.nextLine();
			
			if(choice == 1)
			{
				System.out.println("\n== Rent a car == ");
				System.out.println("Enter your Name : ");
				String customerName = riddhi.nextLine();
				
				System.out.println("\n Available cars");
				for(Car car : cars)
				{
					if(car.isAvailable())
					{
						System.out.println(car.getCarId()+"-"+car.getCarBrand()+" "+car.getCarModel());
					}
				}
				System.out.println("\nEnter the car ID you want to rent : ");
				String carId = riddhi.nextLine();
				
				System.out.println("Enter the number of days for rental : ");
				int rentalDays = riddhi.nextInt();
				riddhi.nextLine();
				
				Customer newCustomer = new Customer("CUS"+(customers.size()+1),customerName);
				addCustomer(newCustomer);
				
				Car selectedCar = null;
				for(Car car : cars)
				{
					if(car.getCarId().equals(carId) && car.isAvailable())
					{
						selectedCar = car;
						break;
					}
				}
				
				if(selectedCar != null )
				{
					double totalPrice = selectedCar.calculatePrice(rentalDays);
					System.out.println("\n== Rental Information ==\n");
					System.out.println("Customer ID : "+newCustomer.getCustomerId());
					System.out.println("Customer Name : "+newCustomer.getName());
					System.out.println("Car : "+selectedCar.getCarBrand()+" "+selectedCar.getCarModel());
					System.out.println("Rental Days : "+rentalDays);
					System.out.println("TotalPrice : rs "+totalPrice);
					System.out.print("\n Confirm rental (Y/N)");
					String confirm = riddhi.nextLine();
					
					if(confirm.equalsIgnoreCase("Y"))
					{
					     rentCar(selectedCar , newCustomer , rentalDays);
					     System.out.println("\n Car rented Successfully.\n");
					}
					else
					{
						System.out.println("\n Rental Cancelled \n");
					}
				}
				else
				{
					System.out.println("\n Invalid car selection or car not available for rent.\n");
				}
			}
			else if(choice == 2 )
			{
				System.out.println("Return a car \n");
				System.out.println("Enter a car ID You want to return : ");
				String carId = riddhi.nextLine();
				
				Car carToReturn = null;
				for(Car car : cars)
				{
					if(car.getCarId().equals(carId) && !car.isAvailable())
					{
						carToReturn = car;
						break;
					}
				}
				
				if(carToReturn != null)
				{
					Customer customer = null;
					for(Rental rental : rentals)
					{
						if(rental.getcar() == carToReturn)
						{
							customer = rental.getcustomer();
							break;
						}
					}
					if(customer != null)
					{
						returnCar(carToReturn);
						System.out.println("Car returned successfully by "+customer.getName());
					}
					else
					{
						System.out.println("Car was not rented or rental information is missing.");
					}
				}
				else
				{
					System.out.println("Invalid car Id or car is not rented.");
				}
			}	
				else if(choice == 3)
				{
					break;
				}
				else
				{
					System.out.println("Invalid choice .Please enter a valid option .");
				}
			}
			System.out.println("\n Thank You for using the car Rental System.");
		}
	}
