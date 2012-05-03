# SQP vs. Semismooth-Newton

Directory `src` contains Java codes
to make the [Matlab][] test files
for the two algorithms.

The codes are maintained using [Eclipse][]
  
  * **main** is a package for the Java codes
  
  * **data** contains .xml files defining the test problems and functions.
    It also contains .tpl files as template for the [Matlab][] test files.

---

## How to use the codes?

1. Make a new Java Project in eclipse. In my case it's called **SQP-vs.-SSN**.

2. Copy the complete git repository **SQP-vs.-Semismooth-Newton**
   into the folder of the project, i.e. **workspace/SQP-vs.-SSN**.

3. Make linked folder under the source folder **src** into the folder data and main.

4. To populate the test files, just run the main method in Main.java

---

## Configuration file

The most configurations are saved in the file **test.config**.
The class *Main* in Main.java needs this file and it will search for it
under the repository **SQP-vs.-Semismooth-Newton**.
If you want to change this, you should change the property *configFile*
in this class.

[matlab]: http://de.wikipedia.org/wiki/MATLAB "MATLAB"
[eclipse]: http://eclipse.org "Eclipse"