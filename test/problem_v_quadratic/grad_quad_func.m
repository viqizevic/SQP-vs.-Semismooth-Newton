function g = grad_quad_func(x)
	Q = [2 0; 0 2];
	c = 8;
	q = [-4; -4];
	g = Q*x + q;
end