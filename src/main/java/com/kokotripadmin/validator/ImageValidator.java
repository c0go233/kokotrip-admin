package com.kokotripadmin.validator;

import com.kokotripadmin.viewmodel.common.BaseImageVm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ImageValidator extends BaseValidator implements ConstraintValidator<ImageConstraint, List<BaseImageVm>> {
    @Override
    public void initialize(ImageConstraint constraintAnnotation) {

    }

    @Override
    public boolean isValid(List<BaseImageVm> baseImageList, ConstraintValidatorContext constraintValidatorContext) {

        if (baseImageList == null || baseImageList.size() == 0) return true;

        int numOfRepImage = 0;
        for (BaseImageVm baseImageVm : baseImageList) {
            if (baseImageVm.isRepImage()) numOfRepImage++;
        }

        if (numOfRepImage <= 0) {
            setErrorMessage(constraintValidatorContext, "대표이미지를 선택해주세요");
            return false;
        } else if (numOfRepImage > 1) {
            setErrorMessage(constraintValidatorContext, "대표이미지는 한개만 선택할수있습니다");
            return false;
        }

        return false;
    }


}
