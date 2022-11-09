package com.baz.wizeline.learningspring.utils;


import com.baz.wizeline.learningspring.model.BankAccountDTO;

import java.util.List;

@FunctionalInterface
public interface GenerateAccounts<T>{
    List<T> generateDummysccounts();
}
