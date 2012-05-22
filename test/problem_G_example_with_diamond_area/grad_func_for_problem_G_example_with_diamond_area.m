function g = grad_func_for_problem_G_example_with_diamond_area(x)
	Q = [2 0; 0 2];
	c = 0;
	q = [-4; -4];
	g = Q*x + q;
end