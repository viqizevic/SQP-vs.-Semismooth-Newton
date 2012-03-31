function g = grad_dixon_func_1(x)
	n = 10;
	g = approx_gradient('dixon_func_1',x,0.001);
end