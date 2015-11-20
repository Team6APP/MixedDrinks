package com.theironyard.controllers;

import com.theironyard.entities.Drink;
import com.theironyard.entities.User;
import com.theironyard.services.DrinkRepository;
import com.theironyard.services.UserRepository;
import com.theironyard.utils.PasswordHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Jack on 11/19/15.
 */

@RestController
public class MixedDrinksController {
    @Autowired
    DrinkRepository drinks;
    @Autowired
    UserRepository users;

    @PostConstruct
    public void init() throws InvalidKeySpecException, NoSuchAlgorithmException, FileNotFoundException {
        User admin = users.findOneByUsername("Admin");
        if (admin == null) {
            admin = new User();
            admin.username = " Admin";
            admin.password = PasswordHash.createHash("Admin");
            users.save(admin);
        }

        if (drinks.count() == 0) {
            Drink drink = new Drink();
            drink.name = "Dark and Stormy";
            drink.ingredient1 = "1 oz Dark Rum";
            drink.ingredient2 = "Ginger Beer";
            drink.ingredient3 = "Lime";
            drink.user = admin;
            drinks.save(drink);
        }
    }

/*
        if (drinks.count() == 0) {
            Scanner scanner = new Scanner(new File ("mixeddrinks.csv"));
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] columns = line.split(",");

                Drink drink = new Drink();

                drink.name = columns[1];
                drink.ingredient1 = columns[2];
                drink.ingredient2 = columns[3];
                if (columns[4] != null) {
                    drink.ingredient3 = columns[4];
                }
                if (columns[5] != null) {
                    drink.ingredient4 = columns[5];
                }
                drink.ingredient5 = columns[6];
                drink.ingredient6 = columns[7];
                drink.ingredient7 = columns[8];
                drink.ingredient8 = columns[9];
                drink.ingredient9 = columns[10];
                drink.ingredient10 = columns[11];
                drink.ingredient11 = columns[12];
                drink.ingredient12 = columns[13];

                drink.user = admin;
                drinks.save(drink);
            }
        }*/


    @RequestMapping("/drinks")
    public List<Drink> drinks () {
        return (List<Drink>) drinks.findAll();
    }
}