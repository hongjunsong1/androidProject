package com.fhh.technology.module.login;

import android.content.Context;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.afollestad.materialdialogs.MaterialDialog;
import com.fhh.technology.R;
import com.fhh.technology.base.BaseActivity;
import com.fhh.technology.module.main.MainActivity;
import com.fhh.technology.utils.ToastUtil;

import butterknife.BindView;

/**
 * desc:登录页
 * Created by fhh on 2018/9/14
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginContract.View {

    @BindView(R.id.et_numbers)
    EditText mEtNumbers;
    @BindView(R.id.et_passwords)
    EditText mEtPasswords;
    @BindView(R.id.ib_delete_number)
    ImageButton mIbDeleteNumber;
    @BindView(R.id.ib_delete_password)
    ImageButton mIbDeletePassword;
    @BindView(R.id.ib_hide_password)
    ImageButton mIbHidePassword;
    @BindView(R.id.btn_login)
    Button mBtnLogin;


    private boolean showPassword;
    private LoginPresenter mPresenter;

    public static void start(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @Override
    public int setContentLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initToolBar() {

    }

    @Override
    public void initPresenter() {
        if (mPresenter == null) {
            mPresenter = new LoginPresenter(this, this);
        }
    }

    @Override
    public void initData() {
        initListener();
    }

    @Override
    public void onDestroyActivity() {
        mPresenter = null;
    }

    private void initListener() {
        mEtNumbers.setText("12312322211");
        mEtPasswords.setText("123456");
        mIbDeleteNumber.setOnClickListener(this);
        mIbDeletePassword.setOnClickListener(this);
        mIbHidePassword.setOnClickListener(this);
        mBtnLogin.setOnClickListener(this);
        mEtNumbers.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    mIbDeleteNumber.setVisibility(View.VISIBLE);
                } else {
                    mIbDeleteNumber.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mEtPasswords.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    mIbDeletePassword.setVisibility(View.VISIBLE);
                    mIbHidePassword.setVisibility(View.VISIBLE);
                } else {
                    mIbDeletePassword.setVisibility(View.GONE);
                    mIbHidePassword.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_delete_number:
                mEtNumbers.setText(null);
                mIbDeleteNumber.setVisibility(View.GONE);
                break;
            case R.id.ib_delete_password:
                mEtPasswords.setText(null);
                mIbDeletePassword.setVisibility(View.GONE);
                mIbHidePassword.setVisibility(View.GONE);
                break;
            case R.id.ib_hide_password:
                if (showPassword) {
                    mIbHidePassword.setBackground(getResources().getDrawable(R.drawable.ic_login_eye_open));
                    mEtPasswords.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    showPassword = false;
                    mEtPasswords.setSelection(mEtPasswords.getText().length());
                } else {
                    mIbHidePassword.setBackground(getResources().getDrawable(R.drawable.ic_login_eye_close));
                    mEtPasswords.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    showPassword = true;
                    mEtPasswords.setSelection(mEtPasswords.getText().length());
                }
                break;
            case R.id.btn_login:
                String number = mEtNumbers.getText().toString().trim();
                String password = mEtPasswords.getText().toString().trim();
                if (mPresenter != null) {
                    mPresenter.manageNumberAndPassword(number, password);
                }
                break;
        }
    }

    @Override
    public void errorRemind(int title, int reeId) {
        new MaterialDialog.Builder(this)
                .title(title)
                .content(reeId)
                .positiveColor(getResources().getColor(R.color.btn_login_color))
                .positiveText(R.string.login_dialog_confirm)
                .show();
    }

    @Override
    public void loginSuccess(int successRemind) {
        ToastUtil.showToast(this, successRemind);
        MainActivity.start(this);
        finish();
    }
}