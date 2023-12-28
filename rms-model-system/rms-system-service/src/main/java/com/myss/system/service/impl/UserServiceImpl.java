package com.myss.system.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.myss.commons.constants.Constants;
import com.myss.commons.enums.ResultCode;
import com.myss.commons.model.vo.system.UserVo;
import com.myss.commons.model.dto.LoginDto;
import com.myss.commons.model.vo.system.LoginVo;
import com.myss.commons.model.ResponseResult;
import com.myss.commons.utils.AuthInfoHolder;
import com.myss.commons.utils.BeanHelper;
import com.myss.commons.utils.JwtUtils;
import com.myss.system.domain.User;
import com.myss.system.mapper.UserMapper;
import com.myss.system.service.UserService;
import com.myss.web.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhurongxu
 * @version 1.0.0
 * @date 2023/12/04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    /**
     *
     */
    @Autowired
    private RedisTemplate redisTemplate;


    /**
     * 用户登录
     *
     * @param loginDto
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult<LoginVo> login(LoginDto loginDto) {
        // 1.非空判断
        String password = loginDto.getPassword();
        String phone = loginDto.getPhone();
        // 有一个不合法，就返回自定义异常
        if (StrUtil.isBlank(phone) || StrUtil.isBlank(password)) {
            throw new CustomException(ResultCode.DATA_ERROR);
        }
        //
        String md5Password = SecureUtil.md5(password);
        // 2.根据username+utype查询数据库
        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(com.myss.system.domain.User::getPhone, phone);
        qw.eq(com.myss.system.domain.User::getPassword, md5Password);
        User user = this.getOne(qw); // 调用本类的方法
        // 3.判断用户是否存在
        if (user == null) {
            throw new CustomException(ResultCode.LOGIN_ERROR);
        }
        // 5.登录成功，jwt制作token
        Map userMap = new HashMap();
        userMap.put("userId", user.getId());
        userMap.put("nickname",user.getNickname());
        String token = JwtUtils.createToken(userMap);
        // 6.user存入redis（敏感数据+有效期）
        redisTemplate.opsForValue().set(Constants.User.USER_ID + user.getId(), user, Duration.ofMinutes(180));
        // 7.返回结果
        LoginVo vo = new LoginVo();
        vo.setAccessToken(token);
        vo.setNickname(user.getNickname());
        vo.setPhone(user.getPhone());
        return ResponseResult.okResult(vo);
    }

    /**
     * 通过id获取User
     *
     * @param userId 用户id
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult<UserVo> findById(Long userId) {
        User user = (User) redisTemplate.opsForValue().get(Constants.User.USER_ID + userId);
        UserVo user1 = BeanHelper.copyProperties(user, UserVo.class);
        return ResponseResult.okResult(user1);
    }

    /**
     * 修改密码
     *
     * @param dto DTO
     * @return {@link ResponseResult}
     */
    @Override
    public ResponseResult<String> updateByPassword(LoginDto dto) {

        Long userId = AuthInfoHolder.getUserId();

        String confirmPwd = dto.getConfirmPassword();
        String password = dto.getPassword();
        String phone = dto.getPhone();

        if (StrUtil.isBlank(password) || StrUtil.isBlank(phone)) {
            throw new CustomException(ResultCode.DATA_ERROR);
        }


        if (!StrUtil.equals(confirmPwd, password)) {
            throw new CustomException(ResultCode.LOGIN_ERROR);
        }

        LambdaQueryWrapper<User> qw = new LambdaQueryWrapper<>();
        qw.eq(com.myss.system.domain.User::getPhone, phone);
        qw.eq(com.myss.system.domain.User::getId, userId);
        User user = this.getOne(qw);


        String md5 = SecureUtil.md5(dto.getPassword());
        user.setPassword(md5);

        this.updateById(user);

        return ResponseResult.okResult();
    }
}




