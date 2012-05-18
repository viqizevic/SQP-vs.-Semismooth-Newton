function H = hess_quad_func(x)
	Q = 2*eye(2);
	q = -2*[4; 7];
	H = Q;
end