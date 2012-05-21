function g = grad_quad_func_4(x)
	Q = 2*[1 0 -1 0; 0 1 0 -1; -1 0 1 0; 0 -1 0 1];
	c = 0;
	q = [0; 0; 0; 0];
	g = Q*x + q;
end