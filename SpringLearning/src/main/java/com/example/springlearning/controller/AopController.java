package com.example.springlearning.controller;

import java.util.Date;

import com.example.springlearning.annotation.Hello;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 匠承
 * @Date: 2024/11/4 23:26
 */
@Controller
public class AopController {

    @RequestMapping(value = "/aoptest", method = RequestMethod.GET)
    public Date test(@RequestParam(value = "input", required = false) String input) {

        System.out.println("in test, where input is " + input);
        test2(input);
        return new Date();
    }

    @Hello(myName = true)
    public String test2(String input) {
        System.out.println("test input");
        return "abc";
    }
}
