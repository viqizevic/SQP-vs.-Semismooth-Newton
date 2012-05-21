function g = grad_quad_func_8(x)
	Q = 2*[2 1 1; 1 2 0; 1 0 1];
	c = 9;
	q = [-8; -6; -4];
	g = Q*x + q;
end