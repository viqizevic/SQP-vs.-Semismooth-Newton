function g = grad_exp_func(x)
	g = approx_gradient('exp_func',x,0.001);
end