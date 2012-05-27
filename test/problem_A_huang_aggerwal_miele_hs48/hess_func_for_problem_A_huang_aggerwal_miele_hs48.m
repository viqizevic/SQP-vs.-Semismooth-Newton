function H = hess_func_for_problem_A_huang_aggerwal_miele_hs48(x)
	Q = 2*[1 0 0 0 0; 0 1 -1 0 0; 0 -1 1 0 0; 0 0 0 1 -1; 0 0 0 -1 1];
	q = [-2; 0; 0; 0; 0];
	c = 1;
	H = Q;
end