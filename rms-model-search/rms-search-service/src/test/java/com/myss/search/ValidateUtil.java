package com.myss.search;

import cn.hutool.core.util.StrUtil;
import com.myss.commons.enums.ResultCode;
import com.myss.commons.model.vo.flie.FileVo;
import com.myss.web.exception.CustomException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ValidateUtil {

    public static void main(String[] args) {

        FileVo fileVo = new FileVo(1L, null,
                null, null,
                null, null,
                null, null,
                null, null);
        List<String> valid = ValidateUtil.valid(fileVo, FileVo.Save.class);

        System.out.println(StrUtil.join(",", valid));
    }

    public static <T> List<String> valid(T t) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> errors =
                    validator.validate(t);
            return errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        } catch (Exception e) {
            throw new CustomException(ResultCode.DATA_ERROR);
        }
    }

    public static <T> List<String> valid(T t, Class<?> zclass) {
        try (ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = validatorFactory.getValidator();
            Set<ConstraintViolation<T>> errors =
                    validator.validate(t, zclass);
            return errors.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());

        } catch (Exception e) {
            throw new CustomException(ResultCode.DATA_ERROR);
        }
    }
}
