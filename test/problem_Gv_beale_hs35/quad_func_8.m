function y = quad_func_8(x)
	Q = 2*[2 1 1; 1 2 0; 1 0 1];
	c = 9;
	q = [-8; -6; -4];
	y = 0.5*x'*Q*x + q'*x + c;
end