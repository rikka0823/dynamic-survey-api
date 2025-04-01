package com.example.quiz11.service.ifs;

import com.example.quiz11.vo.AccountReq;
import com.example.quiz11.vo.BasicRes;

public interface AccountService {

    BasicRes createAccount(AccountReq req);

    BasicRes login(AccountReq req);
}
