package com.example.springlearning.controller;

import com.example.springlearning.model.ValueObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 匠承
 * @Date: 2023/5/16 23:10
 */
@Controller
public class MyController {

    /**
     * 这个Controller是想测试一下，使用VO的方式，是否可以正确传参。<br/>
     * 同时，也测试了，当VO和@RequestParam共存的时候，是否可以正确传参。<br/>
     * @param vo
     * @param type
     */
    @RequestMapping(value = "/test/vo", method = RequestMethod.GET)
    public void testVo(ValueObject vo, @RequestParam(value = "type", required=false) String type) {
        System.out.println("=== hello");

        System.out.println(vo.getId());
        System.out.println(vo.getName());
        System.out.println(vo.getAddress());

        System.out.println("type = " + type);

        System.out.println("=== end");
    }
}
