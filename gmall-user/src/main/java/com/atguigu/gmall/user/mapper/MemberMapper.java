package com.atguigu.gmall.user.mapper;

import com.atguigu.gmall.user.bean.UmsMember;
import java.util.List;

public interface MemberMapper {

    List<UmsMember> selectAllMembers();
}
