package com.propane_company.propane_company;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/")
@Transactional
public class HomeController {
    // https://www.geeksforgeeks.org/spring-boot-thymeleaf-with-example/#
    @GetMapping(path="")
    public String home() {
        return "home";
    }

    @PostMapping("/search_orders")
    public String lookupOrder(@RequestParam String phone, @RequestParam String address, Model model) {
        System.out.println(phone);
        System.out.println(address);

        List<Customer> customers = customerRepository.findByPhoneNumberAndDeliveryAddress(phone, address);
        List<HashMap> tanks = new ArrayList<>();

        for (Customer customer : customers) {
            System.out.println(customer.getId());

            List<Order> orders = orderRepository.findByCustomerId(customer.getId());

            for (Order order : orders) {
                Integer quantity = order.getQuantity();
                List<PropaneTank> propaneTanks = order.getPropaneTanks();

                for (PropaneTank propaneTank : propaneTanks) {
                    HashMap<String, String> tankInfo = new HashMap<>();
                    tankInfo.put("id", Integer.toString(propaneTank.getPropaneTankId()));
                    tankInfo.put("size", Integer.toString(propaneTank.getTankSize()));
                    tankInfo.put("quantity", Integer.toString(quantity));
                    tankInfo.put("status", propaneTank.getDeliveryStatus());
                    tankInfo.put("order_id", Integer.toString(order.getOrderId()));
                    tankInfo.put("order_date", propaneTank.getDeliveryDate().toString());

                    tanks.add(tankInfo);
                }
            }
        }

        ModelAndView modelAndView = new ModelAndView("home");

        System.out.println("TANK AMOUNT");
        System.out.println(tanks.size());

        model.addAttribute("hashMapList", tanks);

        return "orderLookup";
    }

    @PostMapping("/create_order")
    public ModelAndView createOrder(@RequestParam String customerName, @RequestParam String companyName,
                                    @RequestParam String deliveryAddress, @RequestParam String phoneNumber,
                                    @RequestParam Integer tankSize, @RequestParam Integer quantity, @RequestParam String email,
                                    @RequestParam String deliveryDate)
    {
        System.out.println(customerName);
        System.out.println(companyName);
        System.out.println(deliveryAddress);
        System.out.println(phoneNumber);
        System.out.println(tankSize);
        System.out.println(quantity);
        System.out.println(email);
        System.out.println(deliveryDate);

        Customer customer = new Customer();

        customer.setEmail(email);
        customer.setCompanyName(companyName);
        customer.setPhoneNumber(phoneNumber);
        customer.setFullName(customerName);
        customer.setDeliveryAddress(deliveryAddress);

        Customer savedCustomer = customerRepository.save(customer);

        Order order = new Order();

        order.setCustomerId(savedCustomer.getId());
        order.setQuantity(quantity);

        Order savedOrder = orderRepository.save(order);

        List<PropaneTank> propaneTanks = new ArrayList<>();

        for (int i=0; i < quantity; i++) {
            PropaneTank propaneTank = new PropaneTank();

            // Parse the string into a LocalDate object
            LocalDate localDate = LocalDate.parse(deliveryDate, DateTimeFormatter.ISO_DATE);
            Date sqlDate = Date.valueOf(localDate);

            propaneTank.setTankSize(tankSize);
            propaneTank.setDeliveryStatus("pending");
            propaneTank.setOrder(savedOrder);
            propaneTank.setDeliveryDate(sqlDate);

            propaneTanks.add(propaneTank);
        }

        order.setPropaneTanks(propaneTanks);

        orderRepository.save(savedOrder);

        // https://www.baeldung.com/spring-mvc-model-model-map-model-view
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message", "Order Created");

        return modelAndView;
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping(path = "/order_lookup")
    public String orderLookup(){


        return "orderLookup";
    }


}
