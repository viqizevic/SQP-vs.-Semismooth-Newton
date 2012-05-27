function g = grad_func_for_problem_G_example_13_2_antoniou_lu(x)
	Q = 2*[1 0 -1 0; 0 1 0 -1; -1 0 1 0; 0 -1 0 1];
	q = [0; 0; 0; 0];
	c = 0;
	g = Q*x + q;
end