package com.zhuinden.examplegithubclient.presentation.paths.login;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuinden.examplegithubclient.R;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesKey;
import com.zhuinden.examplegithubclient.util.BundleFactory;
import com.zhuinden.examplegithubclient.util.DaggerService;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import flowless.ActivityUtils;
import flowless.Bundleable;
import flowless.Direction;
import flowless.Flow;
import flowless.History;
import flowless.preset.FlowLifecycles;

/**
 * Created by Zhuinden on 2016.12.10..
 */

public class LoginView
        extends RelativeLayout
        implements LoginPresenter.ViewContract, FlowLifecycles.ViewLifecycleListener, Bundleable {
    public LoginView(Context context) {
        super(context);
        init();
    }

    public LoginView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoginView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public LoginView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    public void init() {
        if(!isInEditMode()) {
            LoginComponent loginComponent = DaggerService.getComponent(getContext());
            loginComponent.inject(this);
        }
    }

    @Inject
    LoginPresenter loginPresenter;

    @BindView(R.id.login_username)
    TextView username;

    @BindView(R.id.login_password)
    TextView password;

    @OnTextChanged(R.id.login_username)
    public void onUsernameChanged(Editable username) {
        loginPresenter.updateUsername(username.toString());
    }

    @OnTextChanged(R.id.login_password)
    public void onPasswordChanged(Editable password) {
        loginPresenter.updatePassword(password.toString());
    }

    @OnClick(R.id.login_login)
    public void login() {
        loginPresenter.login();
    }

    ProgressDialog progressDialog;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if(!isInEditMode()) {
            ButterKnife.bind(this);
        }
    }

    @Override
    public void handleLoginSuccess() {
        Flow.get(this).setHistory(History.single(RepositoriesKey.create()), Direction.FORWARD);
    }

    @Override
    public void handleLoginError() {
        Toast.makeText(getContext(), R.string.failed_to_login, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setUsername(String username) {
        this.username.setText(username);
    }

    @Override
    public void setPassword(String password) {
        this.password.setText(password);
    }

    @Override
    public void showLoading() {
        hideLoading();
        progressDialog = new ProgressDialog(ActivityUtils.getActivity(getContext()));
        progressDialog.setMessage(getContext().getString(R.string.please_wait));
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    @Override
    public void hideLoading() {
        if(progressDialog != null) {
            progressDialog.hide();
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    public void onViewRestored() {
        loginPresenter.attachView(this);
    }

    @Override
    public void onViewDestroyed(boolean removedByFlow) {
        loginPresenter.detachView();
        hideLoading();
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = BundleFactory.create();
        bundle.putBundle("PRESENTER_STATE", loginPresenter.toBundle());
        return bundle;
    }

    @Override
    public void fromBundle(@Nullable Bundle bundle) {
        if(bundle != null) {
            loginPresenter.fromBundle(bundle.getBundle("PRESENTER_STATE"));
        }
    }
}
