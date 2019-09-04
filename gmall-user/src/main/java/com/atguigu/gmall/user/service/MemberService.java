package com.atguigu.gmall.user.service;

import com.atguigu.gmall.user.bean.UmsMember;
import com.atguigu.gmall.user.bean.UmsMemberReceiveAddress;
import java.util.List;

public interface MemberService {

    List<UmsMember> getAllMembers();

    List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(long memberId);
}
