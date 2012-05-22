function y = func_for_problem_v_quadratic(x)
	Q = [2 0; 0 2];
	c = 8;
	q = [-4; -4];
	y = 0.5*x'*Q*x + q'*x + c;
end