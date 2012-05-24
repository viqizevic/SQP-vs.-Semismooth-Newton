function y = func_for_problem_A_huang_aggerwal_miele_hs48(x)
	Q = 2*[1 0 0 0 0; 0 1 -1 0 0; 0 -1 1 0 0; 0 0 0 1 -1; 0 0 0 -1 1];
	c = 1;
	q = [-2; 0; 0; 0; 0];
	y = 0.5*x'*Q*x + q'*x + c;
end