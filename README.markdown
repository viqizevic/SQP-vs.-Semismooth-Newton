# SQP vs. Semismooth Newton

This is a project for a *bachelor thesis* using [wiki: Matlab]
comparing two methods of the nonlinear optimization:
[wiki: SQP] and **Semismooth-Newton**.

---

## Directories tree
    |-- SQP-vs.-Semismooth-Newton
        |-- extra
        |-- tex
        |-- test
            |-- 1st_test_problem_func
            |-- ...
            |-- n-th_test_problem_func
            |-- test-populator
                |-- src
                    |-- data
                    |-- main

The main directory **SQP-vs.-Semismooth-Newton** contains
three subdirectories, three Matlab files and this file.
The three Matlab files are:
  1. seq_quad_prog.m (The implementation of SQP-method)
  2. semismooth_newton.m (The implementation of Semismooth-Newton-Method)
  3. active_set_strategy.m (The implementation of Active-Set-Strategy)
The three subdirectories are:
  * Directory **extra** contains other Matlab files
  * Directory **tex** contains the LaTeX files for the bachelor thesis
  * Directory **test** contains Matlab and Java files for the test
    * The Matlab files in this directory with the prefix *test_all* can be used to
      run all the test files.
    * Each test problem as Matlab test file can be found in the different subdirectories.
      Every test problem get his own directory.
      Some problems can used the same objective function.
      The first problem, which used one objective function, is in directory
      '[function_name]_func'.  
      Other problem, which used the same objective function, is in directory
      '[function_name]_func_[number]'.
      With [number] as a suffix to make it differ from the first problem.
      Example: We have two problems with the same function *Rosenbrock*  
      but have different box constraints.
      Thus, the first problem can be found in directory *rosenbrock_func*,
      the second problem in *rosenbrock_func_1*,
      the third one in *rosenbrock_func_2*, and so on.
    * The directory **test_populator** contains source code in Java
      to create the different Matlab test files.

---

[About me](http://homepages.math.tu-berlin.de/~tanzil)
[Email](mailto:vicky.tanzil@googlemail.com)