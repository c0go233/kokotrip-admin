package com.kokotripadmin.viewmodel.exception;

public class ExceptionViewModel {

    private String exceptionName;
    private String errorMessage;
    private StackTraceElement[] stackTraceElements;

    public ExceptionViewModel(String exceptionName, String errorMessage, StackTraceElement[] stackTraceElements) {
        this.exceptionName = exceptionName;
        this.errorMessage = errorMessage;
        this.stackTraceElements = stackTraceElements;
    }

    public String getExceptionName() {
        return exceptionName;
    }

    public void setExceptionName(String exceptionName) {
        this.exceptionName = exceptionName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public StackTraceElement[] getStackTraceElements() {
        return stackTraceElements;
    }

    public void setStackTraceElements(StackTraceElement[] stackTraceElements) {
        this.stackTraceElements = stackTraceElements;
    }
}
