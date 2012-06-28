# SQP vs. Semismooth-Newton

This is a project for a *bachelor thesis* using [MATLAB][]
to make a comparison between two methods of the nonlinear optimization:
**Sequential Quadratic Programming** and **Semismooth Newton**.

---

## Directories tree

    |-- SQP-vs.-Semismooth-Newton
        |-- tex
        |-- test
            |-- problem_...
            |-- ...
            |-- problem_...
            |-- extras
            |-- test-populator
                |-- src
                    |-- data
                    |-- main

The main directory **SQP-vs.-Semismooth-Newton** contains
two subdirectories, three Matlab files, one config file and this README file.

The three Matlab files are:

  1. `seq_quad_prog.m` (The implementation of SQP method)
  
  2. `semismooth_newton.m` (The implementation of Semismooth Newton method)
  
  3. `active_set_strategy.m` (The implementation of Active Set Strategy)
  
The two subdirectories are:
  
  * Directory **tex** contains the LaTeX files for the bachelor thesis.
  
  * Directory **test** contains MATLAB and Java files for the test.
    
    * The MATLAB files in this directory with the prefix `test_all` can be used to run all the test files.
    
    * Each test problem as MATLAB test file can be found in the different subdirectories.
      Every test problem get his own directory.
      
    * The directory **test_populator** contains source code in Java
      to create all the MATLAB test files.
      
    * The directory **extras** here contains other MATLAB files needed for the test
      or for the results interpretation.

---

* <vicky.tanzil@googlemail.com>
* <http://homepages.math.tu-berlin.de/~tanzil>

[matlab]: http://de.wikipedia.org/wiki/MATLAB "MATLAB"