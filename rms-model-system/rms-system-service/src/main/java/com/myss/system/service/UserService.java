package com.myss.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.myss.commons.model.vo.system.UserVo;
import com.myss.commons.model.dto.LoginDto;
import com.myss.commons.model.vo.system.LoginVo;
import com.myss.commons.model.ResponseResult;
import com.myss.system.domain.User;


/**
 * 用户服务
 *
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/28
 */
public interface UserService extends IService<User> {


    /**
     * 用户登录
     *
     * @param loginDto 登录DTO
     * @return {@link ResponseResult}<{@link LoginVo}>
     */
    ResponseResult<LoginVo> login(LoginDto loginDto);

    /**
     * 通过id获取User
     *
     * @param userId 用户 ID
     * @return {@link ResponseResult}<{@link UserVo}>
     */
    ResponseResult<UserVo> findById(Long userId);

    /**
     * 修改密码
     *
     * @param loginDto 登录DTO
     * @return {@link ResponseResult}<{@link String}>
     */
    ResponseResult<String> updateByPassword(LoginDto loginDto);
}
