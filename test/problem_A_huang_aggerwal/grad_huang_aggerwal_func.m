function g = grad_huang_aggerwal_func(x)
	g = approx_gradient('huang_aggerwal_func',x,0.00001);
end