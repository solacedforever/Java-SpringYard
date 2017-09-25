package com.example.customer.controller;

import com.example.customer.model.Customer;
import com.example.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
  @Autowired
  private CustomerService customerService;

  @RequestMapping("/customers")
  String getCustomers(Model model) {
    List<Customer> customers = customerService.get();
    model.addAttribute("listOfCustomers", customers);
    return "view_customers";
  }

  @RequestMapping("/loggedout")
  String logout(Model model) {
    List<Customer> customers = customerService.get();
    model.addAttribute("listOfCustomers", customers);
    return "view_customers";
  }

  @GetMapping("/login")
  String login() {
    return "login";
  }


  @GetMapping("/customer")
  String getCustomer(Model model) {
    showPrinciple();
    return "new_customer";
  }

  @PostMapping("/customer")
  String addCustomer(Customer customer, Model model) {
    customerService.add(customer);
    List<Customer> customers = customerService.get();
    model.addAttribute("listOfCustomers", customers);
    return "view_customers";
  }

  @GetMapping("/admins-only")
  String admins() {
    return "administration";
  }

  @GetMapping("/customer/{customerId}")
  String viewCustomerDetails(@PathVariable String customerId, Model model) {
    Customer aCustomer = customerService.getById(Integer.parseInt(customerId));
    model.addAttribute("customer",aCustomer);
    return "customer_details";
  }

  @ExceptionHandler(value = Exception.class)
  public String handleDefaultErrors(final Exception exception, Model model) {
    System.out.println(exception);
    model.addAttribute("message", exception.getMessage());
    return "error_message";
  }

  private void showPrinciple() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    System.out.println("\n\n\n ===========>");
    System.out.println("name " + authentication.getName());
    System.out.println("details " + authentication.getDetails());
    System.out.println("authorities " + authentication.getAuthorities());
    System.out.println("credentials " + authentication.getCredentials());
    System.out.println("principal " + authentication.getPrincipal());
    System.out.println("is authenticated " + authentication.isAuthenticated());
  }
}