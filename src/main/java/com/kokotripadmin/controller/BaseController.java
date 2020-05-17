package com.kokotripadmin.controller;

import com.kokotripadmin.constant.AppConstant;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

public class BaseController {

    protected final String BUCKET_NAME = "bucketName";


    protected void setRedirectAttributes(RedirectAttributes redirectAttributes,
                                         BindingResult bindingResult,
                                         String exceptionMessage) {
        String packageName = bindingResult.getClass().getPackageName();
        String objectName = bindingResult.getObjectName();
        String bindingResultName = packageName + ".BindingResult." + objectName;
        redirectAttributes.addFlashAttribute(bindingResultName, bindingResult);
        redirectAttributes.addFlashAttribute(AppConstant.EXCEPTION, exceptionMessage);

    }

    protected String getBaseUrl() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
    }
}
