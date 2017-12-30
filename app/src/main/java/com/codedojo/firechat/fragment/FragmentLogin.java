package com.codedojo.firechat.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.codedojo.firechat.R;
import com.codedojo.firechat.interfaces.onAuthRequestListener;
import com.codedojo.firechat.MyApplication;
import com.codedojo.firechat.network.AuthRequest;

import com.codedojo.firechat.interfaces.onAuthPageListener;
import com.codedojo.firechat.utils.Constan;
import com.codedojo.firechat.utils.InputTextListener;

public class FragmentLogin extends Fragment {

    onAuthPageListener registerpage;

    @BindViews({R.id.inputEmail, R.id.inputPass})
    List<TextInputLayout> inputText;

    @BindViews({R.id.loginEmail, R.id.loginPass})
    List<EditText> loginEditText;

    @BindView(R.id.loading)
    ProgressBar loading;

    @BindView(R.id.rootView)
    View rootView;

    public FragmentLogin() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        for (int i = 0; i < inputText.size(); i++) {
            loginEditText.get(i).addTextChangedListener(new InputTextListener(inputText.get(i)));
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        registerpage = (onAuthPageListener) context;
    }

    @OnClick(R.id.txtSignUp)
    public void registerPage(View view) {
        registerpage.onRegisterPage();
    }

    @OnClick(R.id.btnSignIn)
    public void signIn(final View v) {

        String Email = loginEditText.get(0).getText().toString();
        String Password = loginEditText.get(1).getText().toString();

        if (Email.isEmpty()) {
            inputText.get(0).setError("Masukkan Email");
            loginEditText.get(0).requestFocus();
            return;
        }

        if (!Constan.isEmailValid(Email)) {
            inputText.get(0).setError("Invalid Email");
            loginEditText.get(0).requestFocus();
            return;
        }

        if (Password.isEmpty()) {
            inputText.get(1).setError("Masukkan Password");
            loginEditText.get(1).requestFocus();
            return;
        }

        MyApplication.hideSoftInput(getActivity(), loginEditText.get(0));

        v.setEnabled(false);
        setInputTextEnabled(false);

        AuthRequest.Field(Email, Password)
                .startLogin(new onAuthRequestListener() {
                    @Override
                    public void onAuthFailed(String Error) {

                        v.setEnabled(true);
                        setInputTextEnabled(true);

                        switch (Error) {

                            case Constan.ERR_EMAIL_NOT_EXISTS:
                                inputText.get(0).setError("Email Yang Kamu Masukkan Tidak Terdaftar");
                                loginEditText.get(0).requestFocus();
                                break;
                            case Constan.ERR_PASS:
                                inputText.get(1).setError("Password Yang Kamu Masukkan Salah");
                                loginEditText.get(1).requestFocus();
                                break;
                            default:
                                showSnacbar(Error);
                                break;
                        }

                    }

                    @Override
                    public void onAuthSuccess() {
                        registerpage.onAuthenticateSuccess();
                    }

                });

    }

    private void setInputTextEnabled(boolean state) {
        for (int i = 0; i < inputText.size(); i++) {
            inputText.get(i).setEnabled(state);
        }

        if (state) {
            loading.setVisibility(View.GONE);
        } else {
            loading.setVisibility(View.VISIBLE);
        }
    }

    private void showSnacbar(String Pesan) {
        Snackbar.make(rootView, Pesan, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

//        AuthRequest.removeSignInListener();
    }
}
