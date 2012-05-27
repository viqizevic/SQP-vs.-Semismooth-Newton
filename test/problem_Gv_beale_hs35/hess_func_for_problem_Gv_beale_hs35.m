function H = hess_func_for_problem_Gv_beale_hs35(x)
	Q = 2*[2 1 1; 1 2 0; 1 0 1];
	q = [-8; -6; -4];
	c = 9;
	H = Q;
end