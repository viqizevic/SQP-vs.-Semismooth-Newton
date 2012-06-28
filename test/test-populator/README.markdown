# SQP vs. Semismooth-Newton

The directory `src` contains Java codes
to make the [Matlab][] test files
for the two algorithms.

The codes are maintained using [Eclipse][]
  
  * **main** is a package for the Java codes
  
  * **data** contains `.xml` files defining the test problems and functions.
    It also contains `.tpl` files as template for the [Matlab][] test files.

---

## How to use the codes?

1. Make a new Java Project in eclipse. In my case it's called **SQP-vs.-SSN**.

2. Copy the complete git repository **SQP-vs.-Semismooth-Newton**
   into the folder of the project, i.e. **workspace/SQP-vs.-SSN**.

3. Make linked folder under the source folder **src** into the folder data and main.

4. To populate the test files, just run the main method in `Main.java`.

---

## Configuration file

The most configurations are saved in the file `test.config`.
The class *Main* in Main.java needs this file and it will search for it
under the repository **SQP-vs.-Semismooth-Newton**.
If you want to change this, you should change the property *configFile*
in this class.

---

## How to add a new test problem?

Add the objective function of the problem
in the file `functions.xml` in folder **data**.
Add the problem in a file `problems.xml` in **data**.
See examples of this in folder **data**.
Run the *Main* class in `Main.java` to populate the test files.

You can find a folder under **test** with a unique name for the new problem.
To find the test files under [Matlab][] you have to
tell it to add the new folder into the it's search paths.
Configure this under *File -> Set Paths*.
Or in our case, you can just enter the command
`addpath(genpath('test'));`
(You should be in the directory **SQP-vs.-Semismooth-Newton**).

---

## How to add a new test file?

Add a template file for the test in folder **data**.
The template file should have the extension `.tpl`.
Add a new line in `test.config` like this:
`test_template_file_name_<n> = <test_file_name>`

`<n>` should be the next number available for the template file,
that means if there are 3 template files already, then the number 0,1 and 2
cannot be used anymore, thus `<n>` should be 3.
`<test_file_name>` is the name of the template file without the extension.
The name should contain the word *problem*, since the word problem will be replaced
by the name of the test problem.
It is recommended that the name of the template file begin with *test_problem*.

The template test file can contain variables in it,
which will be replaced depend on the information given in each test problem.
The variable should be in the form: `{var_<variable_name>}`,
where `<variable_name}` is the name of the variable.
It should always begin with the string `{var_` and end with the character `}`.
Example: `{var_x0}` for the variable x0.

The list of the variable can be found under the file `TestFileCreator.java`,
precisely under the function *getTestFileContentUsingTemplate*.
You can add here a new variable, if you need one.


[matlab]: http://de.wikipedia.org/wiki/MATLAB "MATLAB"
[eclipse]: http://eclipse.org "Eclipse"