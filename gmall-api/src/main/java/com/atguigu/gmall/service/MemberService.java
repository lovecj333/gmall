package com.atguigu.gmall.service;

import com.atguigu.gmall.bean.UmsMember;
import com.atguigu.gmall.bean.UmsMemberReceiveAddress;
import java.util.List;

public interface MemberService {

    List<UmsMember> getAllMembers();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(long memberId);
}
