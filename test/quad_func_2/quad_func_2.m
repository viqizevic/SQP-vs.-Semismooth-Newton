function y = quad_func_2(x)
	Q = [2 0; 0 2];
	q = [-4; -4];
	y = 0.5*x'*Q*x + q'*x;
end