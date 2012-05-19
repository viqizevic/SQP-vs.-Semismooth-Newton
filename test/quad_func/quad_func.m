function y = quad_func(x)
	Q = [6 2 1; 2 5 2; 1 2 4];
	q = [-8; -3; -3];
	y = 0.5*x'*Q*x + q'*x;
end