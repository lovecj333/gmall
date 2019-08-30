package com.atguigu.gmall.user.controller;

import com.atguigu.gmall.user.bean.UmsMember;
import com.atguigu.gmall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping("index")
    @ResponseBody
    public String index(){
        return "hello member";
    }

    @RequestMapping("getAllMembers")
    @ResponseBody
    public List<UmsMember> getAllMembers(){
        return memberService.getAllMembers();
    }
}
