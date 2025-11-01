package ru.nikita.QuickOrderBot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.nikita.QuickOrderBot.entity.Food;
import ru.nikita.QuickOrderBot.repository.FoodRepository;

import java.util.List;

@Controller
public class FoodControllerView {

    private final FoodRepository foodRepository;

    @Autowired
    public FoodControllerView(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    @GetMapping("/view/foods")
    public String foodList(Model model) {
        List<Food> foods = foodRepository.findAll();
        model.addAttribute("foods", foods);
        return "foodList";
    }
}
