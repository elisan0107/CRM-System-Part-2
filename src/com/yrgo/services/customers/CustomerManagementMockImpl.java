package com.yrgo.services.customers;

import java.util.*;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	private HashMap<String,Customer> customerMap;

	public CustomerManagementMockImpl() { // created Customers inside the constructor is test-data //GPT4
		customerMap = new HashMap<String,Customer>();
		customerMap.put("OB74", new Customer("OB74" ,"Fargo Ltd", "some notes"));
		customerMap.put("NV10", new Customer("NV10" ,"North Ltd", "some other notes"));
		customerMap.put("RM210", new Customer("RM210" ,"River Ltd", "some more notes"));
	}

	@Override
	public void newCustomer(Customer newCustomer) {
		// Retrieve the customer's ID dynamically using the correct method name
		String customerId = newCustomer.getCustomerId();

		// Store the customer in the map using the ID as the key
		customerMap.put(customerId, newCustomer);
	}
	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		// Check if the customer exists in the map
		if (customerMap.containsKey(customerId)) {
			return customerMap.get(customerId); // Return the customer if found
		} else {
			// Thrown i exceptiof the customer is not found
			throw new CustomerNotFoundException();
		}
	}



	@Override
	public void updateCustomer(Customer customer) {
		// Loop through the customerMap to find the matching customer by ID
		for (Map.Entry<String, Customer> entry : customerMap.entrySet()) {
			if (entry.getKey().equals(customer.getCustomerId())) {
				// Found the matching customer
				Customer existingCustomer = entry.getValue();
				Scanner scanner = new Scanner(System.in);

				try {
					// Update each field using user input
					System.out.print("Enter new company name (leave blank to keep current): ");
					String companyName = scanner.nextLine();
					if (!companyName.isBlank()) {
						existingCustomer.setCompanyName(companyName);
					}

					System.out.print("Enter new email (leave blank to keep current): ");
					String email = scanner.nextLine();
					if (!email.isBlank()) {
						existingCustomer.setEmail(email);
					}

					System.out.print("Enter new telephone (leave blank to keep current): ");
					String telephone = scanner.nextLine();
					if (!telephone.isBlank()) {
						existingCustomer.setTelephone(telephone);
					}

					System.out.print("Enter new notes (leave blank to keep current): ");
					String notes = scanner.nextLine();
					if (!notes.isBlank()) {
						existingCustomer.setNotes(notes);
					}

					System.out.println("Customer updated successfully!");
				} catch (Exception e) {
					System.err.println("Error updating customer: " + e.getMessage());
				}

				break; // Exit the loop once the customer is updated
			}
		}
	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		// Use the customer's ID to remove it from the map
		String customerId = oldCustomer.getCustomerId();
		customerMap.remove(customerId);
	}

	@Override
	public List<Customer> findCustomersByName(String searchName) {
		// Create a list to store customers with matching names
		List<Customer> matchingCustomers = new ArrayList<>();

		// Loop through all entries in the customerMap
		for (Map.Entry<String, Customer> entry : customerMap.entrySet()) {
			// Retrieve the customer's company name
			String customerName = entry.getValue().getCompanyName();

			// Compare the provided search name with the customer name
			if (searchName.equals(customerName)) {
				matchingCustomers.add(entry.getValue()); // Add matching customer to the list
			}
		}

		// Return the list of matching customers
		return matchingCustomers;
	}


	@Override
	public List<Customer> getAllCustomers() {
		// Create a new list and add all customers from the map
		return new ArrayList<>(customerMap.values());
	}


	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		// Check if the customer exists in the map
		if (customerMap.containsKey(customerId)) {
			// Return the customer details
			return customerMap.get(customerId);
		} else {
			// Throw an exception if the customer is not found
			throw new CustomerNotFoundException();
		}
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		// First, find the customer by their ID
		Customer customer = customerMap.get(customerId);

		// Check if the customer exists
		if (customer == null) {
			throw new CustomerNotFoundException();
		}

		// Add the call details to the customer's list of calls
		customer.addCall(callDetails);
	}
}
