function g = grad_mccormick_func(x)
	g = approx_gradient('mccormick_func',x,0.001);
end