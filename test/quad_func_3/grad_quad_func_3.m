function g = grad_quad_func_3(x)
	Q = 2*[1 0 -1 0; 0 1 0 -1; -1 0 1 0; 0 -1 0 1];
	q = [0; 0; 0; 0];
	g = Q*x + q;
end