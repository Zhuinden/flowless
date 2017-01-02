package com.zhuinden.examplegithubclient.presentation.paths.login;

import com.zhuinden.examplegithubclient.util.conditionwatcher.Instruction;

/**
 * Created by F1sherKK on 15/04/16.
 */
public class LoginWaitForDialogInstruction
        extends Instruction {
    LoginPresenter loginPresenter;

    public LoginWaitForDialogInstruction(LoginPresenter loginPresenter) {
        this.loginPresenter = loginPresenter;
    }

    @Override
    public String getDescription() {
        return "Loading dialog shouldn't be in view hierarchy";
    }

    @Override
    public boolean checkCondition() {
        return !LoginPresenter.isLoading;
    }
}