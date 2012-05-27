function g = grad_func_for_problem_A_huang_aggerwal_hs28(x)
	Q = 2*[1 1 0; 1 2 1; 0 1 1];
	q = [0; 0; 0];
	c = 0;
	g = Q*x + q;
end