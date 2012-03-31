function g = grad_bazaraa_shetty_func(x)
	g = approx_gradient('bazaraa_shetty_func',x,0.001);
end