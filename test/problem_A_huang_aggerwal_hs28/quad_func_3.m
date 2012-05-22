function y = quad_func_3(x)
	Q = 2*[1 1 0; 1 2 1; 0 1 1];
	c = 0;
	q = [0; 0; 0];
	y = 0.5*x'*Q*x + q'*x + c;
end