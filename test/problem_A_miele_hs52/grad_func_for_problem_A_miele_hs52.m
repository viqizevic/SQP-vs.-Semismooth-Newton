function g = grad_func_for_problem_A_miele_hs52(x)
	Q = 2*[16 -4 0 0 0; -4 2 1 0 0; 0 1 1 0 0; 0 0 0 1 0; 0 0 0 0 1];
	q = [0; -4; -4; -2; -2];
	c = 6;
	g = Q*x + q;
end