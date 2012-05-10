function y = quad_func(x)
	Q = 2*eye(2);
	q = -2*[4; 7];
	y = 0.5*x'*Q*x + q'*x;
end