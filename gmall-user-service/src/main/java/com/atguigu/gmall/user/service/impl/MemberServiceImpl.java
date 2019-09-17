package com.atguigu.gmall.user.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.service.MemberService;
import com.atguigu.gmall.user.mapper.MemberMapper;
import com.atguigu.gmall.user.mapper.MemberReceiveAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private MemberReceiveAddressMapper memberReceiveAddressMapper;

    @Override
    public List<UmsMember> getAllMembers() {
        return memberMapper.selectAllMembers();
    }

    @Override
    public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(long memberId) {
        UmsMemberReceiveAddress memberReceiveAddress = new UmsMemberReceiveAddress();
        memberReceiveAddress.setMemberId(memberId);
        return memberReceiveAddressMapper.select(memberReceiveAddress);
    }
}
