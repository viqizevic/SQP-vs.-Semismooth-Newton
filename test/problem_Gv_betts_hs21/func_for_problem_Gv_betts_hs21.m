function y = func_for_problem_Gv_betts_hs21(x)
	Q = 2*[0.1 0; 0 1];
	q = [0; 0];
	c = -100;
	y = 0.5*x'*Q*x + q'*x + c;
end