function g = grad_dixon_func(x)
	n = 10;
	g = approx_gradient('dixon_func',x,0.001);
end