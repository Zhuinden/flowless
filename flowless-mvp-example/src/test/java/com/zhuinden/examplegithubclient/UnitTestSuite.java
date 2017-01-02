package com.zhuinden.examplegithubclient;

import com.zhuinden.examplegithubclient.presentation.paths.login.LoginTestSuite;
import com.zhuinden.examplegithubclient.presentation.paths.repositories.RepositoriesTestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by Zhuinden on 2016.12.19..
 */

@RunWith(Suite.class)
@Suite.SuiteClasses({LoginTestSuite.class, RepositoriesTestSuite.class})
public class UnitTestSuite {
}
