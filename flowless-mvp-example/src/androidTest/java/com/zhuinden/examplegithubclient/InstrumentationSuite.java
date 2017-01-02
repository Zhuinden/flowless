package com.zhuinden.examplegithubclient;

import com.zhuinden.examplegithubclient.presentation.activity.main.MainInstrumentedTest;
import com.zhuinden.examplegithubclient.presentation.paths.login.LoginInstrumentedTest;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesInstrumentedTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Zhuinden on 2016.12.19..
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({MainInstrumentedTest.class, LoginInstrumentedTest.class, RepositoriesInstrumentedTest.class})
public class InstrumentationSuite {
}
