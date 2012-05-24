function H = hess_func_for_problem_A_simple_example(x)
	xd = [0; 0; 0; 0; 0];
	H = 2*eye(length(x));
end