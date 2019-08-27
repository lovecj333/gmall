package com.atguigu.gmall.user.service.impl;

import com.atguigu.gmall.user.mapper.MemberMapper;
import com.atguigu.gmall.user.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService{

    @Autowired
    private MemberMapper memberMapper;
}
