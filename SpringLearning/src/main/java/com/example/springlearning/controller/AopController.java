package com.example.springlearning.controller;

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

    @Hello(myName = true)
    @RequestMapping(value = "/aoptest", method = RequestMethod.GET)
    public void test(@RequestParam(value = "input", required = false) String input) {

        System.out.println("in test, where input is " + input);
    }
}
