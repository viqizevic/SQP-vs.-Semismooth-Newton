function H = hess_quad_func_4(x)
	Q = 2*[1 0 0 0 0; 0 1 -1 0 0; 0 -1 1 0 0; 0 0 0 1 -1; 0 0 0 -1 1];
	c = 1;
	q = [-2; 0; 0; 0; 0];
	H = Q;
end