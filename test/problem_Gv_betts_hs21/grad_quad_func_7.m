function g = grad_quad_func_7(x)
	Q = 2*[0.1 0; 0 1];
	c = -100;
	q = [0; 0];
	g = Q*x + q;
end