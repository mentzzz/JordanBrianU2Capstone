package com.company.adminservice.service;
import com.company.adminservice.dto.Customer;
import org.junit.Before;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

public class CustomerServiceLayerTest {

    CustomerServiceLayer service;


    @Before
    public void setUp() throws Exception {
        setUpCustomerServiceMock();
    }

    public void setUpCustomerServiceMock() {

        service = mock(CustomerServiceLayer.class);

        Customer customer = new Customer();
        customer.setId(1);
        customer.setFirstName("J");
        customer.setLastName("M");
        customer.setStreet("Mam");
        customer.setCity("Char");
        customer.setZip("28262");
        customer.setEmail("me@me.com");
        customer.setPhone("336-755-5555");

        Customer customer1 = new Customer();
        customer1.setFirstName("J");
        customer1.setLastName("M");
        customer1.setStreet("Mam");
        customer1.setCity("Char");
        customer1.setZip("28262");
        customer1.setEmail("me@me.com");
        customer1.setPhone("336-755-5555");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        doReturn(customer).when(service).createCustomer(customer1);
        doReturn(customer).when(service).findCustomer(1);
        doReturn(customers).when(service).findAllCustomers();


    }


    @Test
    public void saveFindFindAllCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("J");
        customer.setLastName("M");
        customer.setStreet("Mam");
        customer.setCity("Char");
        customer.setZip("28262");
        customer.setEmail("me@me.com");
        customer.setPhone("336-755-5555");

        customer = service.createCustomer(customer);
        Customer fromService = service.findCustomer(customer.getId());
        List<Customer> customers = service.findAllCustomers();

        assertEquals(customer, fromService);
        assertEquals(customers.size(), 1);
    }


}