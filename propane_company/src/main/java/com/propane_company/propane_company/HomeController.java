package com.propane_company.propane_company;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/")
@Transactional
public class HomeController {
    // https://www.geeksforgeeks.org/spring-boot-thymeleaf-with-example/#
    @GetMapping(path="")
    public String home() {
        return "home";
    }

    @GetMapping("/login")
    public ModelAndView loginAdmin(HttpSession session) {
        Administrator administrator = (Administrator) session.getAttribute("administrator");

        if (administrator == null) {
            return new ModelAndView("login");
        }
        else {
            return new ModelAndView("alreadyLoggedIn");
        }
    }

    @PostMapping("/search_orders")
    public String lookupOrder(@RequestParam String phone, @RequestParam String address,Model model, HttpSession session) {
        System.out.println(phone);
        System.out.println(address);

        System.out.println("IS ADMIN");

        List<Customer> customerList = customerRepository.findByPhoneNumberAndDeliveryAddress(phone, address);
        List<HashMap> tanks = new ArrayList<>();

        for (Customer customer : customerList) {
            System.out.println(customer.getId());

            List<Order> orders = orderRepository.findByCustomerId(customer.getId());

            for (Order order : orders) {
                Integer quantity = order.getQuantity();
                List<PropaneTank> propaneTanks = order.getPropaneTanks();

                for (PropaneTank propaneTank : propaneTanks) {
                    HashMap<String, String> tank = new HashMap<>();
                    tank.put("id", Integer.toString(propaneTank.getPropaneTankId()));
                    tank.put("size", Integer.toString(propaneTank.getTankSize()));
                    tank.put("quantity", Integer.toString(quantity));
                    tank.put("status", propaneTank.getDeliveryStatus());
                    tank.put("order_id", Integer.toString(order.getOrderId()));
                    tank.put("order_date", propaneTank.getDeliveryDate().toString());
                    tank.put("status", propaneTank.getDeliveryStatus());

                    tanks.add(tank);
                }
            }
        }

        System.out.println("TANK AMOUNT");
        System.out.println(tanks.size());

        Administrator administrator = (Administrator) session.getAttribute("administrator");


        if (administrator != null) {
            model.addAttribute("isAdmin", true);
        }
        else {
            model.addAttribute("isAdmin", false);
        }

        model.addAttribute("hashMapList", tanks);

        return "orderLookup";
    }

    @PostMapping("/update_status")
    public ModelAndView updateOrderStatus(@RequestParam Integer tankId, @RequestParam String status){
        ModelAndView modelAndView = new ModelAndView("updateStatus");
        HashMap<String, String> map = new HashMap<>();

        map.put("tank_id", tankId.toString());

        modelAndView.addObject("map", map);

        try {
            PropaneTank propaneTank = propaneTankRepository.findByPropaneTankId(tankId);

            if (status.equals("complete")) {
                Notification notif = new Notification();

                notif.setNotificationText("Order # " + propaneTank.getOrder().getOrderId().toString() + " is complete");
                notif.setCustomerId(propaneTank.getOrder().getCustomerId());
                notif.setNotificationId(0);

                Customer customer = customerRepository.findById(propaneTank.getOrder().getCustomerId()).get();

                notif.setPhoneNumber(customer.getPhoneNumber());
                notif.setAddress(customer.getDeliveryAddress());

                notificationsRepository.save(notif);
            }

            propaneTank.setDeliveryStatus(status);
            propaneTankRepository.save(propaneTank);
        }
        catch (Exception e) {

        }

        return modelAndView;
    }

    @GetMapping("/updateStatus/{id}")
    public ModelAndView getStatusUpdate(@PathVariable("id") Integer tankId){
        HashMap<String, String> map = new HashMap<>();

        map.put("tank_id", tankId.toString());

        ModelAndView modelAndView = new ModelAndView("updateStatus");
        modelAndView.addObject("map", map);

        return modelAndView;
    }

    @GetMapping("/updateOrder/{id}")
    public ModelAndView updateOrderAdmin(@PathVariable("id") Integer tankId){
        PropaneTank tank = propaneTankRepository.findByPropaneTankId(tankId);
        Order order = tank.getOrder();

        ModelAndView modelAndView = new ModelAndView("editOrder");

        HashMap<String, String> map = new HashMap<>();

        map.put("tank_id", tank.getPropaneTankId().toString());
        map.put("delivery_date", tank.getDeliveryDate().toString());
        map.put("size", tank.getTankSize().toString());
        map.put("quantity", order.getQuantity().toString());

        modelAndView.addObject("map", map);

        return modelAndView;
    }


    @GetMapping("/notifications")
    public ModelAndView getNotifications(){
        ModelAndView modelAndView = new ModelAndView("notifications");

        return modelAndView;
    }

    @PostMapping("/notifications")
    public ModelAndView getNotificationsByCustomerId(@RequestParam String phoneNumber, @RequestParam String address){
        ModelAndView modelAndView = new ModelAndView("notifications");

        List<Notification> getNotifs = notificationsRepository.findByPhoneNumberAndAddress(phoneNumber, address);

        List<HashMap<String, String>> map = new ArrayList<>();

        for (Notification notification : getNotifs) {
            String notifText = notification.getNotificationText();

            HashMap<String, String> map1 = new HashMap<>();
            map1.put("text", notifText);

            map.add(map1);
        }

        modelAndView.addObject("map", map);

        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView login(@RequestParam String username, @RequestParam String password, HttpSession session){
        Administrator administrator = administratorRepository.findAdministratorByuserNameAndPassword(username, password);

        if (administrator != null) {
            ModelAndView modelAndView = new ModelAndView("orderLookup");
            modelAndView.addObject("isAdmin", true);

            session.setAttribute("administrator", administrator);
            return modelAndView;
        }

        return new ModelAndView("login");
    }

    @PostMapping("/edit_order")
    public ModelAndView updateOrder(@RequestParam Integer tankId, @RequestParam String deliveryDate,
                                    @RequestParam Integer quantity, @RequestParam Integer tankSize){
        ModelAndView modelAndView = new ModelAndView("editOrder");

        PropaneTank tank = propaneTankRepository.findByPropaneTankId(tankId);
        Order order = orderRepository.findById(tank.getOrder().getOrderId()).get();

        if (tank.getDeliveryStatus().equals("pending")) {
            tank.setTankSize(tankSize);

            LocalDate localDate = LocalDate.parse(deliveryDate, DateTimeFormatter.ISO_DATE);
            Date sqlDate = Date.valueOf(localDate);

            tank.setDeliveryDate(sqlDate);
            tank.setTankSize(tankSize);

            order.setQuantity(quantity);
            orderRepository.save(order);
        }

        HashMap<String, String> map = new HashMap<>();

        map.put("tank_id", tank.getPropaneTankId().toString());
        map.put("delivery_date", tank.getDeliveryDate().toString());
        map.put("size", tank.getTankSize().toString());
        map.put("quantity", order.getQuantity().toString());

        modelAndView.addObject("map", map);

        return modelAndView;
    }

    @PostMapping("/create_order")
    public ModelAndView createOrder(@RequestParam String CustomerName, @RequestParam String CompanyName,
                                    @RequestParam String DeliveryAddress, @RequestParam String PhoneNumber,
                                    @RequestParam Integer TankSize, @RequestParam Integer Quantity, @RequestParam String Email,
                                    @RequestParam String DeliveryDate)
    {
        System.out.println(CustomerName);
        System.out.println(CompanyName);
        System.out.println(DeliveryAddress);
        System.out.println(PhoneNumber);
        System.out.println(TankSize);
        System.out.println(Quantity);
        System.out.println(Email);
        System.out.println(DeliveryDate);

        Customer customer = new Customer();

        customer.setEmail(Email);
        customer.setCompanyName(CompanyName);
        customer.setPhoneNumber(PhoneNumber);
        customer.setFullName(CustomerName);
        customer.setDeliveryAddress(DeliveryAddress);

        Customer saved_customer = customerRepository.save(customer);

        Administrator administrator = administratorRepository.findAdministratorByAdminId(1);

        Order order = new Order();

        order.setCustomerId(saved_customer.getId());
        order.setQuantity(Quantity);
        order.setAdministrator(administrator);

        Order result_order = orderRepository.save(order);

        List<PropaneTank> propaneTanks = new ArrayList<>();

        for (int i=0; i < Quantity; i++) {
            PropaneTank propaneTank = new PropaneTank();

            // Parse the string into a LocalDate object
            LocalDate localDate = LocalDate.parse(DeliveryDate, DateTimeFormatter.ISO_DATE);
            Date sqlDate = Date.valueOf(localDate);

            propaneTank.setTankSize(TankSize);
            propaneTank.setDeliveryStatus("pending");
            propaneTank.setOrder(result_order);
            propaneTank.setDeliveryDate(sqlDate);

            propaneTanks.add(propaneTank);
        }

        order.setPropaneTanks(propaneTanks);

        orderRepository.save(result_order);

        // https://www.baeldung.com/spring-mvc-model-model-map-model-view
        ModelAndView modelAndView = new ModelAndView("home");
        modelAndView.addObject("message", "Order Created");

        return modelAndView;
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PropaneTankRepository propaneTankRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @GetMapping(path = "/order_lookup")
    public ModelAndView orderLookup(HttpSession session){
        ModelAndView modelAndView = new ModelAndView("orderLookup");

        Administrator administrator = (Administrator) session.getAttribute("administrator");

        if (administrator != null) {
            modelAndView.addObject("isAdmin", true);
        }
        else {
            modelAndView.addObject("isAdmin", false);
        }

        return modelAndView;
    }


}
