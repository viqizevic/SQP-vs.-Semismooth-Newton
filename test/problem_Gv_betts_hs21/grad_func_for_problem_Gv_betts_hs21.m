function g = grad_func_for_problem_Gv_betts_hs21(x)
	Q = 2*[0.1 0; 0 1];
	c = -100;
	q = [0; 0];
	g = Q*x + q;
end