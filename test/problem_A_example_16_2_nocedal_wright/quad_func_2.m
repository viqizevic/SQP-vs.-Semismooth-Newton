function y = quad_func_2(x)
	Q = [6 2 1; 2 5 2; 1 2 4];
	c = 0;
	q = [-8; -3; -3];
	y = 0.5*x'*Q*x + q'*x + c;
end