function y = func_for_problem_Av_betts_miele_hs53(x)
	Q = 2*[1 -1 0 0 0; -1 2 1 0 0; 0 1 1 0 0; 0 0 0 1 0; 0 0 0 0 1];
	q = [0; -4; -4; -2; -2];
	c = 6;
	y = 0.5*x'*Q*x + q'*x + c;
end