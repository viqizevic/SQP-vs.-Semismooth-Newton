function g = grad_quad_func_2(x)
	Q = [2 0; 0 2];
	q = [-4; -4];
	g = Q*x + q;
end