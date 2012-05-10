function g = grad_quad_func(x)
	Q = 2*eye(2);
	q = -2*[4; 7];
	g = Q*x + q;
end