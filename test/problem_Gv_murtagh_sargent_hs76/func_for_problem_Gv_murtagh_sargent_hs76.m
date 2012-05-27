function y = func_for_problem_Gv_murtagh_sargent_hs76(x)
	Q = 2*[1 0 -0.5 0; 0 0.5 0 0; -0.5 0 1 0.5; 0 0 0.5 0.5];
	q = [-1; -3; 1; -1];
	c = 0;
	y = 0.5*x'*Q*x + q'*x + c;
end