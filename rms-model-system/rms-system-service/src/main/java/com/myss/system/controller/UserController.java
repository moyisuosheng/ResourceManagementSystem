package com.myss.system.controller;

import cn.hutool.core.util.StrUtil;
import com.myss.commons.enums.ResultCode;
import com.myss.commons.model.vo.system.UserVo;
import com.myss.commons.model.dto.LoginDto;
import com.myss.commons.model.vo.system.LoginVo;
import com.myss.commons.model.ResponseResult;
import com.myss.system.service.UserService;
import com.myss.web.exception.CustomException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 用户信息前端控制器
 * </p>
 *
 * @author itheima
 */
@Tag(name = "用户控制器")
@Slf4j
@RestController
@RequestMapping("/v1/public/user/")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @Autowired
//    private ContentClient contentClient;

    /**
     * 用户登录
     *
     * @param dto DTO
     * @return {@link ResponseResult}<{@link LoginVo}>
     */
    @Operation(summary = "用户登录")

    @PostMapping("/login")
    public ResponseResult<LoginVo> login(@RequestBody LoginDto dto) {
        return userService.login(dto);
    }

    /**
     * 通过id获取User
     *
     * @param userId 用户 ID
     * @return {@link ResponseResult}<{@link UserVo}>
     */
    @Operation(summary = "通过id获取User")
    @GetMapping("/findById/{userId}")
    public ResponseResult<UserVo> findById(@PathVariable Long userId) {
        return userService.findById(userId);
    }

    /**
     * 发送验证码
     *
     * @param param 参数
     * @return {@link ResponseResult}<{@link String}>
     */
    @Operation(summary = "发送验证码")
    @GetMapping("/common/smsMsg")
    public ResponseResult<String> smsMsg(@RequestBody Map<String, String> param) {

        //String phone = param.get("phone");
        //contentClient.smsMsg(phone);

        return ResponseResult.okResult();
    }


    /**
     * 验证验证码
     *
     * @param param 参数
     * @return {@link ResponseResult}
     */
    @Operation(summary = "验证验证码")
    @PostMapping("/user/changePwd/verify")
    public ResponseResult verify(Map<String, String> param) {

        String phone = param.get("phone");
        String code = param.get("code");
        String verifyCody = (String) redisTemplate.opsForValue().get("code:" + phone);

        if (!StrUtil.equals(verifyCody, code)) {
            throw new CustomException(ResultCode.VERIFY_ERROR);
        }
        return ResponseResult.okResult("true");
    }

    /**
     * 修改密码
     *
     * @param dto DTO
     * @return {@link ResponseResult}<{@link String}>
     */
    @Operation(summary = "修改密码")
    @PostMapping("/user/changePwd/{verifyToken}")
    public ResponseResult<String> updateByPassWord(@RequestBody LoginDto dto) {

        return userService.updateByPassword(dto);

    }
}
