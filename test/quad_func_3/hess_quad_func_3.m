function H = hess_quad_func_3(x)
	Q = 2*[1 0 -1 0; 0 1 0 -1; -1 0 1 0; 0 -1 0 1];
	q = [0; 0; 0; 0];
	H = Q;
end