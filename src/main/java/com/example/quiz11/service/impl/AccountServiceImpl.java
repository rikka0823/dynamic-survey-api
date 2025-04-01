package com.example.quiz11.service.impl;

import com.example.quiz11.constants.ResMessage;
import com.example.quiz11.entity.Account;
import com.example.quiz11.repository.AccountDao;
import com.example.quiz11.service.ifs.AccountService;
import com.example.quiz11.vo.AccountReq;
import com.example.quiz11.vo.BasicRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    private AccountDao accountDao;

    @Override
    public BasicRes createAccount(AccountReq req) {
        // 檢查 email 是否已存在
        if (accountDao.existsById(req.getEmail())) {
            return new BasicRes(ResMessage.EMAIL_DUPLICATED.getCode(), ResMessage.EMAIL_DUPLICATED.getMessage());
        }

        // 寫入
        accountDao.save(new Account(req.getEmail(), passwordEncoder.encode(req.getPassword())));

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }

    @Override
    public BasicRes login(AccountReq req) {
        // 取得帳戶
        Optional<Account> accountOptional = accountDao.findById(req.getEmail());

        // 檢查是否存在及密碼是否正確
        if (accountOptional.isEmpty()) {
               return new BasicRes(ResMessage.LOGIN_ERROR.getCode(), ResMessage.LOGIN_ERROR.getMessage());
        }
        if (!passwordEncoder.matches(req.getPassword(), accountOptional.get().getPassword())) {
            return new BasicRes(ResMessage.LOGIN_ERROR.getCode(), ResMessage.LOGIN_ERROR.getMessage());
        }

        return new BasicRes(ResMessage.SUCCESS.getCode(), ResMessage.SUCCESS.getMessage());
    }
}
