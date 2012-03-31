function g = grad_asaadi_func(x)
	g = approx_gradient('asaadi_func',x,0.001);
end