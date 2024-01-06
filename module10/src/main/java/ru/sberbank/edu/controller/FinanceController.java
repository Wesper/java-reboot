package ru.sberbank.edu.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.HashMap;

@Controller
@RequestMapping(value = "/finance")
public class FinanceController {

    @Value("${minSum}")
    private String minSum;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView getFinancialForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/form.jsp");
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView calculateFinancialResult(@RequestParam HashMap<String, String> req) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            double sum = Double.parseDouble(req.get("sum"));
            double percentage = Double.parseDouble(req.get("percentage"));
            double years = Double.parseDouble(req.get("years"));

            if (sum < Double.parseDouble(minSum)) {
                modelAndView.addObject("minSum", minSum);
                modelAndView.setViewName("/smallSumResult.jsp");
                return modelAndView;
            }

            double totalSum = sum + sum * percentage / 100 * years;

            modelAndView.addObject("totalSum", new DecimalFormat("#0.00").format(totalSum));
            modelAndView.setViewName("/correctResult.jsp");
            return modelAndView;
        } catch (NumberFormatException | NullPointerException e) {
            modelAndView.setViewName("/incorrectInputResult.jsp");
            return modelAndView;
        }
    }
}
