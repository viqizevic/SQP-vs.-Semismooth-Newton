function g = grad_quad_func(x)
	xd = 4;
	g = approx_gradient('quad_func',x,0.001);
end