package com.nordic_motorhome_project.controller;

import com.nordic_motorhome_project.model.Booking;
import com.nordic_motorhome_project.model.Customer;
import com.nordic_motorhome_project.model.MotorhomeModel;
import com.nordic_motorhome_project.service.BookingService;
import com.nordic_motorhome_project.service.CustomerService;
import com.nordic_motorhome_project.service.MotorhomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.ChronoUnit;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private MotorhomeService motorhomeService;
    @Autowired
    private CustomerService customerService;

    @GetMapping("/booking")
    public String booking(Model model) {
        //bookings
        List<Booking> bookings = bookingService.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);
        return "home/booking";
    }

    @GetMapping("/addBooking")
    public String addBooking(Model model) {
        //bookings
        List<Booking> bookings = bookingService.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        return "home/addBooking";
    }

    @PostMapping("/addBooking")
    public String addBooking(Model model, @RequestParam String brand, @RequestParam Integer client,
                             @RequestParam String dateStart, @RequestParam String dateEnd,
                             @RequestParam int numberOfPpl, @RequestParam String type) {


        //Passes the list of available motorhomes
        model.addAttribute("booked", bookedMotorhomes(dateStart, dateEnd, brand, numberOfPpl, type));
        //Passes if list of motorhomes is empty
        model.addAttribute("isEmpty", availableExist(bookedMotorhomes(dateStart, dateEnd, brand, numberOfPpl, type)));

        //All motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);

        //All customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        boolean wrongDate = false;

        LocalDate dStart = LocalDate.parse(dateStart);
        LocalDate dEnd = LocalDate.parse(dateEnd);

        if (dStart.isAfter(dEnd)) {
            wrongDate = true;
        }

        //Pass selected values for footer
        model.addAttribute("brand", brand);
        model.addAttribute("client", client);
        model.addAttribute("dateStart1", dateStart);
        model.addAttribute("dateEnd1", dateEnd);
        model.addAttribute("numberOfPpl", numberOfPpl);
        model.addAttribute("type", type);
        model.addAttribute("dateError", wrongDate);


        return "home/addBooking";
    }


    @PostMapping("/createBooking")
    public String createBooking(@RequestParam String motorhomeId, @RequestParam Integer client, @RequestParam String dateStart, @RequestParam String dateEnd) {

        int price = getPrice(dateStart,dateEnd,motorhomeId);
        bookingService.addBooking(motorhomeId, client, dateStart, dateEnd, price);
        return "redirect:/booking";
    }


    @GetMapping("/deleteBooking/{id}")
    public String deleteBooking(@PathVariable("id") int id) {
        bookingService.deleteBooking(id);
        return "redirect:/booking";
    }

    @GetMapping("/editBooking/{id}")
    public String editBooking(@PathVariable("id") int id, Model model) {
        //passing id of booking we will edit
        model.addAttribute("editId", id);

        //bookings
        List<Booking> bookings = bookingService.getBookings();
        model.addAttribute("bookings", bookings);
        //motorhomes
        List<MotorhomeModel> motorhomesList = motorhomeService.getMotorhomes();
        model.addAttribute("motorhomes", motorhomesList);
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);
        return "home/editBooking";
    }


    @PostMapping("/changeBooking")
    public String changeBooking(@RequestParam int client, @RequestParam String dateStart, @RequestParam String dateEnd, @RequestParam String motorhome, @RequestParam int booking, Model model) {

        List<Booking> bookings = bookingService.getBookings();

        boolean wrongDate = false;
        boolean dateTaken = false;

        LocalDate dStart = LocalDate.parse(dateStart);
        LocalDate dEnd = LocalDate.parse(dateEnd);

        if (dStart.isAfter(dEnd)) {
            wrongDate = true;
        }

        for (int i = 0; i < bookings.size(); i++) {
            if (dEnd.isAfter(bookings.get(i).getDate_start()) && dStart.isBefore(bookings.get(i).getDate_end()) && bookings.get(i).getId() != booking && bookings.get(i).getMotorhome_id().equals(motorhome)) {
                dateTaken = true;
            }
            if (dEnd.isAfter(bookings.get(i).getDate_start()) && dEnd.isBefore(bookings.get(i).getDate_end()) && bookings.get(i).getId() != booking && bookings.get(i).getMotorhome_id().equals(motorhome)) {
                dateTaken = true;
            }
            if (dStart.isAfter(bookings.get(i).getDate_start()) && dStart.isBefore(bookings.get(i).getDate_end()) && bookings.get(i).getId() != booking && bookings.get(i).getMotorhome_id().equals(motorhome)) {
                dateTaken = true;
            }
        }

        model.addAttribute("dateTaken", dateTaken);
        model.addAttribute("booking", booking);
        model.addAttribute("client", client);
        model.addAttribute("dateStart", dateStart);
        model.addAttribute("dateEnd", dateEnd);
        model.addAttribute("motorhome", motorhome);
        model.addAttribute("dateError", wrongDate);

        if (wrongDate == false && dateTaken == false) {
            bookingService.editBooking(client, dateStart, dateEnd, booking);
        }
        //customers
        List<Customer> customerList = customerService.getCustomers();
        model.addAttribute("customers", customerList);

        return "home/changeBooking";
    }

    public ArrayList<String> bookedMotorhomes(String dateStart, String dateEnd, String brand, int numberOfPpl, String type) {
        //Lists where we will put unavailable by date motorhomes IDs
        ArrayList<String> dateId = new ArrayList<>();

        //List from where we will delete all unavailable motorhomes
        List<MotorhomeModel> motorhomes = motorhomeService.getMotorhomes();

        //List with available motorhomes IDs
        ArrayList<String> available = new ArrayList<>();

        //Converting date from string to local date
        LocalDate start = LocalDate.parse(dateStart);
        LocalDate end = LocalDate.parse(dateEnd);

        //Adding unavailable by date motorhomes to the list
        List<Booking> bookings = bookingService.getBookings();
        for (int i = 0; i < bookings.size(); i++) {
            if (end.isAfter(bookings.get(i).getDate_start()) && start.isBefore(bookings.get(i).getDate_end())) {
                dateId.add(bookings.get(i).getMotorhome_id());
            }
        }

        for (int i = 0; i < bookings.size(); i++) {
            if (end.isBefore(bookings.get(i).getDate_end()) && start.isAfter(bookings.get(i).getDate_start())) {
                dateId.add(bookings.get(i).getMotorhome_id());
            }
        }

        //Checking by all other parameters
        for (int i = 0; i < motorhomes.size(); i++) {
            if (!brand.equals(motorhomes.get(i).getBrand()) || numberOfPpl != motorhomes.get(i).getNumber_of_persons() || !type.equals(motorhomes.get(i).getIsLuxury())) {
                motorhomes.remove(i);
                i--;
            }
        }

        //Deleting unavailable by date from list
        boolean found = false;
        for (int i = 0; i < motorhomes.size(); i++) {
            found = false;
            for (int j = 0; j < dateId.size(); j++) {
                if (motorhomes.get(i).getLicense_plate().equals(dateId.get(j))) {
                    motorhomes.remove(i);
                    found = true;
                }
            }
            if (found == true) {
                i--;
            }
        }

        //Converting to only license plate - ID list
        for (int i = 0; i < motorhomes.size(); i++) {
            available.add(motorhomes.get(i).getLicense_plate());
        }

        return available;
    }

    public boolean availableExist(ArrayList<String> bookedMotorhomes) {
        return bookedMotorhomes.isEmpty();
    }

    public int getPrice(String dateStart, String dateEnd, String motorhomeId) {
        //Converting date from string to local date
        LocalDate start = LocalDate.parse(dateStart);
        LocalDate end = LocalDate.parse(dateEnd);

        List<MotorhomeModel> motorhomes = motorhomeService.getMotorhomes();

        double price = 0;
        boolean counted = false;
        int countingMonth;
        int startMonth = start.getMonthValue();
        int endMonth = end.getMonthValue();
        int months = (int) ChronoUnit.MONTHS.between(start, end);

        if (months >= 6) {
            if (startMonth == 1 || startMonth == 2 || startMonth == 12) {
                if (endMonth == 1|| endMonth == 2 || endMonth == 12)
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, end);
                            price = (days * motorhomes.get(i).getBase_price());
                        }
                    }
                }else
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, LocalDate.of(start.getYear(),3,1));
                            price += (days * motorhomes.get(i).getBase_price());
                            countingMonth = (startMonth+1);
                            while(!counted)
                            {
                                if(countingMonth==12)
                                {
                                    countingMonth=1;
                                }else
                                {
                                    countingMonth++;
                                }

                                price += priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(1);
                                if((Double.compare(priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(0), 0.0)) == 0)
                                {
                                    counted=true;
                                }
                            }
                        }
                    }
                }
            }else if (startMonth == 6 || startMonth == 7 || startMonth == 8) {
                if (endMonth == 6|| endMonth == 7 || endMonth == 8)
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, end);
                            price = (days * motorhomes.get(i).getBase_price()*1.6);
                        }
                    }
                }else
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, LocalDate.of(start.getYear(),3,1));
                            price += (days * motorhomes.get(i).getBase_price()*1.6);
                            countingMonth = (startMonth+1);
                            while(!counted)
                            {
                                if(countingMonth==12)
                                {
                                    countingMonth=1;
                                }else
                                {
                                    countingMonth++;
                                }

                                price += priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(1);
                                if((Double.compare(priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(0), 0.0)) == 0)
                                {
                                    counted=true;
                                }
                            }
                        }
                    }
                }
            }else if(startMonth == 3 || startMonth == 4 || startMonth == 5) {
                if (endMonth == 3|| endMonth == 4 || endMonth == 5)
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, end);
                            price = (days * motorhomes.get(i).getBase_price()*1.3);
                        }
                    }
                }else
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, LocalDate.of(start.getYear(),3,1));
                            price += (days * motorhomes.get(i).getBase_price()*1.3);
                            countingMonth = (startMonth+1);
                            while(!counted)
                            {
                                if(countingMonth==12)
                                {
                                    countingMonth=1;
                                }else
                                {
                                    countingMonth++;
                                }

                                price += priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(1);
                                if((Double.compare(priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(0), 0.0)) == 0)
                                {
                                    counted=true;
                                }
                            }
                        }
                    }
                }
            }else {
                if (endMonth == 9|| endMonth == 10 || endMonth == 11)
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, end);
                            price = (days * motorhomes.get(i).getBase_price()*1.3);
                        }
                    }
                }else
                {
                    for (int i = 0; i < motorhomes.size(); i++) {
                        if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                            int days = (int) ChronoUnit.DAYS.between(start, LocalDate.of(start.getYear(),3,1));
                            price += (days * motorhomes.get(i).getBase_price()*1.3);
                            countingMonth = (startMonth+1);
                            while(!counted)
                            {
                                if(countingMonth==12)
                                {
                                    countingMonth=1;
                                }else
                                {
                                    countingMonth++;
                                }

                                price += priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(1);
                                if((Double.compare(priceForMonth(countingMonth,motorhomes.get(i).getBase_price(),end).get(0), 0.0)) == 0)
                                {
                                    counted=true;
                                }
                            }
                        }
                    }
                }
            }
        } else {

            for (int i = 0; i < motorhomes.size(); i++) {
                if (motorhomeId.equals(motorhomes.get(i).getLicense_plate())) {
                    int days = (int) ChronoUnit.DAYS.between(start, end);
                    price = (days * motorhomes.get(i).getBase_price());
                }
            }
        }
        int endPrice = (int) price;
        return endPrice;
    }

    public ArrayList<Double> priceForMonth (int month, int pricePerDay, LocalDate endDate)
    {
        ArrayList<Double> pricePerMonth = new ArrayList<>();
        double price;
        double factor;
        if(month == 1 || month == 2 || month == 12)
        {
            factor = 1;
        }else if(month == 3 || month == 4 || month == 5 || month == 9 || month == 10 || month == 11)
        {
            factor = 1.3;
        }else{
            factor = 1.6;
        }

        if(month==endDate.getMonthValue())
        {
            int days = (int) ChronoUnit.DAYS.between(LocalDate.of(endDate.getYear(),month,1), endDate);
            price = (days * pricePerDay*factor);
            pricePerMonth.add(0.0);
            pricePerMonth.add(price);
        } else
        {
            int days = (int) ChronoUnit.DAYS.between(LocalDate.of(endDate.getYear(),month,1), LocalDate.of(endDate.getYear(),(month+1),1));
            price = (days * pricePerDay*factor);
            pricePerMonth.add(1.0);
            pricePerMonth.add(price);
        }
        return pricePerMonth;
    }
}
