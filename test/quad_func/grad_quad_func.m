function g = grad_quad_func(x)
	Q = [6 2 1; 2 5 2; 1 2 4];
	q = [-8; -3; -3];
	g = Q*x + q;
end