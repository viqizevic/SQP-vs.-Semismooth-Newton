function y = quad_func(x)
	Q = [2 0; 0 2];
	c = 8;
	q = [-4; -4];
	y = 0.5*x'*Q*x + q'*x + c;
end