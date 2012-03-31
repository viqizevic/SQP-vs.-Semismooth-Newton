function H = hess_quad_func(x)
	xd = 4;
	H = approx_hessian('quad_func',x,0.001);
end