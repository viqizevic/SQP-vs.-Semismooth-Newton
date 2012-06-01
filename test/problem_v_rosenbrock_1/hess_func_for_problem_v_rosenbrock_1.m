function H = hess_func_for_problem_v_rosenbrock_1(x)
	H = [ 1200*x(1)^2-400*x(2)+2  -400*x(1);
              -400*x(1)              200  ];
end