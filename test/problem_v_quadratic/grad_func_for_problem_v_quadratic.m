function g = grad_func_for_problem_v_quadratic(x)
	Q = [2 0; 0 2];
	q = [-4; -4];
	c = 8;
	g = Q*x + q;
end