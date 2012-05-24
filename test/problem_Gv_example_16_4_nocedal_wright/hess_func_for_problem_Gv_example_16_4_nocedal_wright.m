function H = hess_func_for_problem_Gv_example_16_4_nocedal_wright(x)
	xd = [1; 2.5];
	H = 2*eye(length(x));
end