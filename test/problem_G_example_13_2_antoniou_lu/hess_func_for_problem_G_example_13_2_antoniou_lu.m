function H = hess_func_for_problem_G_example_13_2_antoniou_lu(x)
	Q = 2*[1 0 -1 0; 0 1 0 -1; -1 0 1 0; 0 -1 0 1];
	c = 0;
	q = [0; 0; 0; 0];
	H = Q;
end