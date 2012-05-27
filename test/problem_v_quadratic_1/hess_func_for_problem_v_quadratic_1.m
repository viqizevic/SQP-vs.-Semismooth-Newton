function H = hess_func_for_problem_v_quadratic_1(x)
	Q = [2 0; 0 2];
	q = [-4; -4];
	c = 0;
	H = Q;
end