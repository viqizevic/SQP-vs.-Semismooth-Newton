function g = grad_betts_func(x)
	g = approx_gradient('betts_func',x,0.001);
end