function y = func_for_problem_Gv_beale_hs35(x)
	Q = 2*[2 1 1; 1 2 0; 1 0 1];
	q = [-8; -6; -4];
	c = 9;
	y = 0.5*x'*Q*x + q'*x + c;
end